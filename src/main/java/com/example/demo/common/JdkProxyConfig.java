package com.example.demo.common;

import com.example.demo.service.IUserService;
import com.example.demo.service.JdkProxyHandler;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import java.lang.reflect.Proxy;

@Configuration
@RequiredArgsConstructor
public class JdkProxyConfig {
    private final UserService userService;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public IUserService userServiceJdkProxy() {
        IUserService userServiceProxy = (IUserService) Proxy.newProxyInstance(
                IUserService.class.getClassLoader(),                        // ClassLoader loader,
                new Class[]{IUserService.class},                            // Class<?>[] interfaces,
                new JdkProxyHandler(userService, transactionManager)           // InvocationHandler h
        );
        return userServiceProxy;
    }
}
