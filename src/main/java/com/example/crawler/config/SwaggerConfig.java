package com.example.crawler.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        Info info = new Info();
        info.setTitle("MJ API");
        info.setDescription("명지대학교 학생식당 크롤링");
        info.setVersion("1.0.0");

        return new OpenAPI().info(info);
    }
}
