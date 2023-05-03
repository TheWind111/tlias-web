package com.example;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//让使用了 @ConfigurationProperties 注解的类生效,并且将该类注入到 IOC 容器中,交由 IOC 容器进行管理
@Configuration
@EnableConfigurationProperties(value = AliOSSProperties.class)
public class AliOSSAutoConfiguration {

//    创建AliOSSUtils工具类，并注入AliOSSProperties配置对象
    @Bean
    public AliOSSUtils aliOSSUtils(AliOSSProperties properties) {
        AliOSSUtils aliOSSUtils = new AliOSSUtils();
        aliOSSUtils.setAliOSSProperties(properties);
        return aliOSSUtils;
    }
}
