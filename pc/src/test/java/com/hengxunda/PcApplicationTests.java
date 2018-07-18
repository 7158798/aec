package com.hengxunda;

import com.hengxunda.dao.mapper.UserMapper;
import com.hengxunda.generalservice.service.IChangeMscPriceService;
import com.hengxunda.generalservice.service.IGenerationAwardService;
import com.hengxunda.generalservice.service.ILevelAwardService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PcApplicationTests {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ILevelAwardService levelAwardService;

    @Autowired
    private IGenerationAwardService generationAwardService;

    @Autowired
    private IChangeMscPriceService changeMscPriceService;

	@Test
	public void contextLoads() {
        System.out.println(userMapper.selectByPrimaryKey("123").getName());
	}

	@Test
    public void levelAwardServiceTest(){
        levelAwardService.levelAward("7");
        log.info("主线逻辑继续执行...");
    }

    @Test
    public void genAwardServiceTest(){
        generationAwardService.generationAward("5");
        log.info("主线逻辑继续执行...");
    }

    @Test
    public void changePriceTest(){
        changeMscPriceService.changePrice();
    }

}
