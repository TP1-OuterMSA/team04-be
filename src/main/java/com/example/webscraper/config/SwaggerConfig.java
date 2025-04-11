package com.example.webscraper.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("MJ Cafeteria Feedback API")
                        .description("명지대학교 학생 식당 피드백 API 명세서")
                        .version("1.0.0")
                );
    }
}
