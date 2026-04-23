package com.student.dealshare.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger 配置
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("大学生资源信息分享平台 API")
                        .version("1.0.0")
                        .description("提供用户管理、优惠信息、社区互动、消息通知等功能接口")
                        .contact(new Contact()
                                .name("Student Deal Share Team")
                                .email("support@studentdealshare.com")));
    }
}
