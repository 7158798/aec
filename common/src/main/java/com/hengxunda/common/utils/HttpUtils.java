package com.hengxunda.common.utils;

import com.hengxunda.common.exception.ServerException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.CoreConnectionPNames;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.*;


/**
 * httputil
 */
public class HttpUtils {
    private static final String CHARSET = "UTF-8";

    /**
     * http get请求
     *
     * @param url
     * @return
     */
    public static String get(String url) {
        CloseableHttpClient httpClient = null;
        CloseableHttpResponse response = null;
        try {
            httpClient = HttpClients.createDefault();
            HttpGet httpGet = new HttpGet(url);
            //设置请求和传输超时时间
            RequestConfig requestConfig = RequestConfig.custom().setSocketTimeout(3000)
                    .setConnectionRequestTimeout(3000)
                    .setConnectTimeout(3000).build();
            httpGet.setConfig(requestConfig);
            //httpGet.addHeader("token","8464465"); 添加header信息
            response = httpClient.execute(httpGet);
            HttpEntity entity = response.getEntity();
            if (Objects.nonNull(entity)) {
                String str = EntityUtils.toString(entity, CHARSET);
                return str;
            }
        } catch (Exception e) {
            throw new ServerException("HTTP请求异常", e);
        } finally {
            try {
                if (Objects.nonNull(response)) {
                    response.close();
                }
                if (Objects.nonNull(httpClient)) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        return null;
    }


    /**
     * get
     *
     * @param host
     * @param path
     * @param method
     * @param headers
     * @param querys
     * @return
     * @throws Exception
     */
    public static HttpResponse doGet(String host, String path, String method,
                                     Map<String, String> headers,
                                     Map<String, String> querys)
            throws Exception {
        HttpClient httpClient = wrapClient(host);
        httpClient.getParams().setParameter(CoreConnectionPNames.CONNECTION_TIMEOUT, 2000);//连接时间
        httpClient.getParams().setParameter(CoreConnectionPNames.SO_TIMEOUT, 2000);//数据传输时间
        HttpGet request = new HttpGet(buildUrl(host, path, querys));
        for (Map.Entry<String, String> e : headers.entrySet()) {
            request.addHeader(e.getKey(), e.getValue());
        }

        return httpClient.execute(request);
    }

    private static String buildUrl(String host, String path, Map<String, String> querys) throws UnsupportedEncodingException {
        StringBuilder sbUrl = new StringBuilder();
        sbUrl.append(host);
        if (!StringUtils.isBlank(path)) {
            sbUrl.append(path);
        }
        if (null != querys) {
            StringBuilder sbQuery = new StringBuilder();
            for (Map.Entry<String, String> query : querys.entrySet()) {
                if (0 < sbQuery.length()) {
                    sbQuery.append("&");
                }
                if (StringUtils.isBlank(query.getKey()) && !StringUtils.isBlank(query.getValue())) {
                    sbQuery.append(query.getValue());
                }
                if (!StringUtils.isBlank(query.getKey())) {
                    sbQuery.append(query.getKey());
                    if (!StringUtils.isBlank(query.getValue())) {
                        sbQuery.append("=");
                        sbQuery.append(URLEncoder.encode(query.getValue(), "utf-8"));
                    }
                }
            }
            if (0 < sbQuery.length()) {
                sbUrl.append("?").append(sbQuery);
            }
        }

        return sbUrl.toString();
    }

    private static HttpClient wrapClient(String host) {
        HttpClient httpClient = new DefaultHttpClient();
        if (host.startsWith("https://")) {
            sslClient(httpClient);
        }

        return httpClient;
    }

    private static void sslClient(HttpClient httpClient) {
        try {
            SSLContext ctx = SSLContext.getInstance("TLS");
            X509TrustManager tm = new X509TrustManager() {
                public X509Certificate[] getAcceptedIssuers() {
                    return null;
                }

                public void checkClientTrusted(X509Certificate[] xcs, String str) {

                }

                public void checkServerTrusted(X509Certificate[] xcs, String str) {

                }
            };
            ctx.init(null, new TrustManager[]{tm}, null);
            SSLSocketFactory ssf = new SSLSocketFactory(ctx);
            ssf.setHostnameVerifier(SSLSocketFactory.ALLOW_ALL_HOSTNAME_VERIFIER);
            ClientConnectionManager ccm = httpClient.getConnectionManager();
            SchemeRegistry registry = ccm.getSchemeRegistry();
            registry.register(new Scheme("https", 443, ssf));
        } catch (KeyManagementException ex) {
            throw new RuntimeException(ex);
        } catch (NoSuchAlgorithmException ex) {
            throw new RuntimeException(ex);
        }
    }

    /**
     * http post请求（数据编码格式application/x-www-form-urlencoded）
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return
     */
    public static String post(String url, Map<String, Object> params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();

            for (Iterator<String> iterator = params.keySet().iterator(); iterator.hasNext(); ) {
                String key = iterator.next();
                parameters.add(new BasicNameValuePair(key, params.get(key).toString()));
            }

            UrlEncodedFormEntity urlEncodedFormEntity = new UrlEncodedFormEntity(parameters, CHARSET);
            httpPost.setEntity(urlEncodedFormEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String str = EntityUtils.toString(entity, CHARSET);
                    return str;
                }
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * http post请求（数据编码格式application/x-www-form-urlencoded）
     *
     * @param url    请求地址
     * @param params 请求参数(参数格式："key1=value1&key2=value2")
     * @return
     */
    public static String post(String url, String params) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            StringEntity stringEntity = new StringEntity(params, CHARSET);
            stringEntity.setContentType("application/x-www-form-urlencoded");
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    String str = EntityUtils.toString(entity, CHARSET);
                    return str;
                }
            } finally {
                response.close();
                httpClient.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * http post请求（数据编码格式application/json）
     *
     * @param url  请求地址
     * @param json 请求参数（参数格式为json字符串）
     * @return
     */
    public static String postJson(String url, String json) {
        try {
            CloseableHttpClient httpClient = HttpClients.createDefault();
            HttpPost httpPost = new HttpPost(url);
            //中文乱码
            StringEntity stringEntity = new StringEntity(json, CHARSET);
            //数据编码格式
            stringEntity.setContentType("application/json");
            httpPost.setEntity(stringEntity);
            CloseableHttpResponse response = httpClient.execute(httpPost);
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    return EntityUtils.toString(entity, CHARSET);
                }
            } finally {
                response.close();
                httpClient.close();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


}
