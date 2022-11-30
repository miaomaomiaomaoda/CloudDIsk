package com.example.clouddisk.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author R.Q.
 * brief:Swagger配置类
 */
@Configuration
public class OpenApiConfig {
    @Bean(value = "indexApi")
    public Docket indexApi(){
        return new Docket(DocumentationType.OAS_30)
                .groupName("网站前端接口分组").apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.example.clouddisk.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("网盘项目API")
                .description("基于springboot+vue的web文件系统,旨在为用户提供一个简单方便的文件存储管理方案，能够以可视化界面和完善的目录结构体系，对文件进行存储管理")
                .version("1.0")
                .contact(new Contact("r.q.","https://github.com/miaomaomiaomaoda/CloudDisk","1845423486@qq.com"))
                .build();
    }
}
