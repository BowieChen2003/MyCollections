package com.ccb.backend.configuration;

// 意义：Spring Boot 的启动方式绕过了标准的 Servlet 容器扫描机制，容器不会自动发现 @ServerEndpoint 类

import jakarta.websocket.server.ServerEndpoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class WebSocketConfiguration {

    @Bean
    public ServerEndpointExporter serverEndpointExporter(){
        return new ServerEndpointExporter();
    }
}
