package com.ccb.backend.configuration;

import com.ccb.backend.properties.AliOssProperties;
import com.ccb.backend.utils.AliOssUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class OssConfiguration {
    // @Autowired
    // private AliOssProperties aliOssProperties;
    // 在 @Configuration 配置类 中，@Bean 方法的参数，Spring 会自动从容器中查找匹配的 Bean 并注入进来，等价于写了 @Autowired。

    @Bean   // 将方法的返回值注册为Spring容器中的Bean
    @ConditionalOnMissingBean   // 当容器中没有指定类型的Bean时，创建该Bean
    public AliOssUtil aliOssUtil(AliOssProperties aliOssProperties) {
        log.info("开始创建阿里云文件上传工具类对象: {}", aliOssProperties);
        return new AliOssUtil(
                aliOssProperties.getEndpoint(),
                aliOssProperties.getAccessKeyId(),
                aliOssProperties.getAccessKeySecret(),
                aliOssProperties.getBucketName()
        );
    }
}
