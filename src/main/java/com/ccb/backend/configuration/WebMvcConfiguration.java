package com.ccb.backend.configuration;

import com.ccb.backend.intercepter.JwtIntercepter;
import com.ccb.backend.json.JacksonObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


//@Component
//@Slf4j
//public class WebMvcConfiguration extends WebMvcConfigurationSupport {
//
//    @Autowired
//    private JwtIntercepter jwtIntercepter;
//
//
//    protected void addInterceptors(InterceptorRegistry registry) {
//        log.info("开始注册自定义拦截器...");
//        registry.addInterceptor(jwtIntercepter)
//                .addPathPatterns("/user/**")
//                .excludePathPatterns("/user/user/login")
//                .excludePathPatterns("/user/user/register");
//
//    }
//
//}

@Component
@Slf4j
public class WebMvcConfiguration implements WebMvcConfigurer {  // 改为 implements WebMvcConfigurer

    @Autowired
    private JwtIntercepter jwtIntercepter;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        log.info("开始注册自定义拦截器...");
        registry.addInterceptor(jwtIntercepter)
                .addPathPatterns("/user/**")
                .excludePathPatterns("/user/user/login")
                .excludePathPatterns("/user/user/register");
    }

    // 扩展消息转换器 统一对后端返回的数据进行处理
//    @Override
//    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {  // 注意：方法是public（接口默认）
//        // 创建一个消息转换器对象
//        MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
//        // 设置对象转换器，底层使用Jackson将Java对象转为json
//        converter.setObjectMapper(new JacksonObjectMapper());  // 假设您有这个自定义类
//        // 将自己的消息转化器加入容器中
//        converters.add(0, converter); // 索引设置为0，表示优先使用我们自定义的转换器
//    }

    @Bean
    @Primary
    public JacksonObjectMapper jacksonObjectMapper() {
        return new JacksonObjectMapper();
    }


}
