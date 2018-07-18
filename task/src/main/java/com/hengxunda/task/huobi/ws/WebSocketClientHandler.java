package com.hengxunda.task.huobi.ws;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hengxunda.common.utils.GZipUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

	private final WebSocketClientHandshaker handshaker;
	private ChannelPromise handshakeFuture;
	private MoniterTask moniter;
	private IWebSocketService service;
	private WebSocketBase client;

	public WebSocketClientHandler(WebSocketClientHandshaker handshaker, IWebSocketService service,
			MoniterTask moniter, WebSocketBase client) {
		this.handshaker = handshaker;
		this.service = service;
		this.moniter = moniter;
		this.client = client;
	}

	public ChannelFuture handshakeFuture() {
		return handshakeFuture;
	}

	@Override
	public void handlerAdded(ChannelHandlerContext ctx) {
		handshakeFuture = ctx.newPromise();
	}

	@Override
	public void channelActive(ChannelHandlerContext ctx) {
		handshaker.handshake(ctx.channel());
	}

	@Override
	public void channelInactive(ChannelHandlerContext ctx) {
		log.warn("WebSocket Client disconnected!");
	}

	@Override
	public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		Channel ch = ctx.channel();
		moniter.updateTime();
		if (!handshaker.isHandshakeComplete()) {
			handshaker.finishHandshake(ch, (FullHttpResponse) msg);
			log.info("WebSocket Client connected!");
			handshakeFuture.setSuccess();
			return;
		}

		if (msg instanceof FullHttpResponse) {
			FullHttpResponse response = (FullHttpResponse) msg;
			throw new IllegalStateException("Unexpected FullHttpResponse (getStatus=" + response.getStatus()
					+ ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
		}

		WebSocketFrame frame = (WebSocketFrame) msg;
		if (frame instanceof BinaryWebSocketFrame) {
			BinaryWebSocketFrame binaryFrame = (BinaryWebSocketFrame) frame;
			service.onReceive(decodeByteBuff(binaryFrame.content()));
        }else if (frame instanceof PongWebSocketFrame) {
        	log.info("WebSocket Client received pong");
        } else if (frame instanceof CloseWebSocketFrame) {
        	log.info("WebSocket Client received closing");
            ch.close();
        }
		
		
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
		cause.printStackTrace();
		if (!handshakeFuture.isDone()) {
			handshakeFuture.setFailure(cause);
		}
		ctx.close();
	}

	public JSONObject decodeByteBuff(ByteBuf buf) throws Exception {

		byte[] temp = new byte[buf.readableBytes()];
		buf.readBytes(temp);
		
		temp = GZipUtils.decompress(temp);
		String str = new String(temp,"UTF-8");
		if(str.contains("ping")) {
			client.sendMessage(str.replace("ping", "pong"));
		}
		JSONObject json = JSON.parseObject(str);
		
		return json;
	}
}