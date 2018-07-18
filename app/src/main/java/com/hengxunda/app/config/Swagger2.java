package com.hengxunda.app.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2 {

    @Value("${online:false}")
    private boolean online;

    @Bean
    public Docket createRestApi() {
//        ParameterBuilder parameterBuilder = new ParameterBuilder();
//        List<Parameter> parmList = new ArrayList<>();
//        parameterBuilder.name("token").description("令牌").modelRef(new ModelRef("string")).parameterType("header").required(false).build();
//        parmList.add(parameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .enable(!online)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.hengxunda"))
                .paths(PathSelectors.any())
                .build();
//                .globalOperationParameters(parmList);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("app接口文档")
                .description("说明:所有请求必须在请求头中带上token(登录与注册请求不需要带).")
                .version("1.0")
                .build();
    }

}
