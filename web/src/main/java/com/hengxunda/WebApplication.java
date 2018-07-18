package com.hengxunda;

import lombok.extern.slf4j.Slf4j;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Slf4j
@EnableTransactionManagement
@MapperScan(basePackages = {"com.hengxunda.dao.mapper","com.hengxunda.dao.mapper_custom"})
@SpringBootApplication
@EnableScheduling
@EnableAspectJAutoProxy(exposeProxy = true)
public class WebApplication {

	public static void main(String[] args) {

		SpringApplication.run(WebApplication.class, args);

		log.info("Web Project start success");
	}
}
