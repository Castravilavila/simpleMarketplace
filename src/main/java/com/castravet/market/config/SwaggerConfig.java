package com.castravet.market.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.service.Contact;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public Docket docket(){
        return new Docket(DocumentationType.SWAGGER_2).select()
                .paths(PathSelectors.ant("/api/**"))
                .apis(RequestHandlerSelectors.basePackage("com.castravet.market"))
                .build()
                .apiInfo(apiDetails());
    }

    private ApiInfo apiDetails(){
        return new ApiInfo(
                "Simple Store API",
                "API for Products and Users",
                "1.0",
                "Free Of Use",
                new Contact("Castravet Alexandru","https://www.linkedin.com/in/alexandru-castrave%C8%9B-b3455114b/","sandualist@gmail.com"),
                "No License",
                "No link",
                Collections.emptyList());
    }
}
