package com.example.demo.common;

import com.example.demo.service.CglibProxyHandler;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.cglib.proxy.Enhancer;

@Configuration
@RequiredArgsConstructor
public class CglibProxyConfig {
    private final UserService userService;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public UserService userServiceCglibProxy() {
        UserService userServiceProxy = (UserService) Enhancer.create(
                UserService.class,                                          // Class "superclass"
                new CglibProxyHandler(userService, transactionManager)           // MethodInterceptor "callback"
        );
        return userServiceProxy;
    }
}
