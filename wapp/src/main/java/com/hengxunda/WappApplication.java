package com.hengxunda;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@MapperScan(basePackages = {"com.hengxunda.dao.mapper","com.hengxunda.dao.mapper_custom"})
@SpringBootApplication(scanBasePackages = "com.hengxunda")
public class WappApplication {
	public static void main(String[] args) {
		SpringApplication.run(WappApplication.class, args);
	}
}
