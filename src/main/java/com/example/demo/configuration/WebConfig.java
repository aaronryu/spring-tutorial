package com.example.demo.configuration;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    // 1. 정적 파일(정적 리소스)에 대한 경로 설정 = 리소스 핸들러
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 의미 = /** 로 들어오는 모든 요청을 -> classpath: = /resources 경로안에 있는 /static/ 디렉토리에서 찾아 매핑하겠습니다.
        registry.addResourceHandler("/**")               // 1. 요청 = http://localhost:8080   +        /images/cat.png
                .addResourceLocations("classpath:/static/") // 2. 경로 = classpath: = /resources + /static/images/cat.png
                .setCachePeriod(3600);
    }
}