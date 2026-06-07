package com.ccb.backend.configuration;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import io.swagger.v3.oas.models.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collection;
import java.util.List;

@Configuration
@Slf4j
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .openapi("3.1.0")
                .info(new Info()
                        .title("MyCollections API")  // 可自定义
                        .version("1.0")
                        .description("接口测试文档"));
    }



    @Bean
    public OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser() {
        log.info("[系统启动][SwaggerUI]initBean: customerGlobalHeaderOpenApiCustomiser");
        return new OpenApiCustomizer(){
            @Override
            public void customise(OpenAPI openApi) {
                Paths paths = openApi.getPaths();
                //log.info("[系统启动][SwaggerUI]paths="+paths.toString());
                Collection<PathItem> values = paths.values();
                for (PathItem pathItem : values) {
                    List<Operation> operations = pathItem.readOperations();
                    //
                    for (Operation operation : operations) {
                        HeaderParameter headerParameter = new HeaderParameter();
                        //headerParameter.setRequired(true);
                        headerParameter.setName("token");
                        operation.addParametersItem(headerParameter);
                    }
                }
            }
        };
    }

    @Bean
    public GroupedOpenApi adminApi(OpenApiCustomizer customerGlobalHeaderOpenApiCustomiser) {
        //log.info("[系统启动][SwaggerUI]initBean: adminApi");
        return GroupedOpenApi.builder()
                .group("1-前端接口")
                .pathsToMatch("/**")
                .addOpenApiCustomizer(customerGlobalHeaderOpenApiCustomiser)
                .build();
    }

}
