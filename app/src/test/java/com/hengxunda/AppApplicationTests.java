package com.hengxunda;

import com.hengxunda.common.Enum.GlobalParameterEnum;
import com.hengxunda.common.utils.GsonUtils;
import com.hengxunda.dao.entity.User;
import com.hengxunda.dao.entity.UserSms;
import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.dao.mapper.UserSmsMapper;
import com.hengxunda.dao.mapper_custom.AdvertCustomMapper;
import com.hengxunda.dao.po.app.AdvertPo;
import com.hengxunda.generalservice.common.SerialCodeGenerator;
import com.hengxunda.generalservice.service.IGlobalParameterService;
import com.hengxunda.generalservice.service.IStringRedisService;
import com.hengxunda.wallet.btc.BTCHelper;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class AppApplicationTests {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private UserSmsMapper userSmsMapper;

	@Autowired
	private BTCHelper btcHelper;

	@Autowired
	private IGlobalParameterService iGlobalParameterService;

	@Autowired
	private IStringRedisService iStringRedisService;

	@Autowired
	private StringRedisTemplate stringRedisTemplate;

	@Autowired
	private AdvertCustomMapper advertCustomMapper;

	@Autowired
	private SerialCodeGenerator serialCodeGenerator;


	@Test
	public void t1() {
		User user = userMapper.selectByPrimaryKey("123");
		System.out.println(user.getName());

	}

	@Test
	public void t2(){
		UserSms userSms = userSmsMapper.selectByPrimaryKey("1");
		System.out.println(userSms.getPhone());
	}

	@Test
	public void t3(){
		String str = btcHelper.getAccountAddress("123");
		System.out.println(str);
	}

	@Test
	public void t4(){
		System.out.println(iGlobalParameterService.getGlobalParameterByKey(GlobalParameterEnum.AEC2CNY.getCode()).getCronValue());
	}


	@Test
	public void t5(){

		iStringRedisService.set("blockTest","{\"name\":\"tom\",\"age\":12}");

		System.out.println(stringRedisTemplate.getExpire("blockTest"));

	}

	@Test
	public void t6(){
		iStringRedisService.set("blockTest22","{\"name\":\"tom\",\"age\":12}",1, TimeUnit.MINUTES);
		System.out.println(stringRedisTemplate.getExpire("blockTest22"));
	}

	@Test
	public void t7(){

		//iStringRedisService.set("blockTest","5");
		stringRedisTemplate.boundValueOps("blockTest").increment(1);
	}

	@Test
	public void t8(){

		stringRedisTemplate.setEnableTransactionSupport(true);
		stringRedisTemplate.watch("blockTest");
		stringRedisTemplate.multi();
		stringRedisTemplate.boundValueOps("blockTest").increment(1);
		stringRedisTemplate.exec();

	}

	@Test
	public void t9(){
		System.out.println(serialCodeGenerator.getNext());
		System.out.println(serialCodeGenerator.getNext().length());
	}

}
