package com.example.demo.common;

import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.transaction.PlatformTransactionManager;

import org.springframework.aop.framework.ProxyFactoryBean;

@Configuration
@RequiredArgsConstructor
public class SpringProxyConfig {
    private final UserService userService;
    private final PlatformTransactionManager transactionManager;

    @Bean
    @Primary
    public ProxyFactoryBean userServiceProxyFactoryBean() {
        ProxyFactoryBean userServiceProxyFactoryBean = new ProxyFactoryBean();
//      userServiceProxyFactoryBean.setProxyInterfaces(new Class[]{IUserService.class});
        userServiceProxyFactoryBean.setTarget(userService);
        userServiceProxyFactoryBean.setInterceptorNames("springProxyHandler");
        return userServiceProxyFactoryBean;
    }
}
