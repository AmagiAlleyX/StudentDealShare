package com.student.dealshare.admin;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

/**
 * 后台管理模块启动类
 * 
 * @author student-deal-share
 * @since 1.0.0
 */
@SpringBootApplication
@MapperScan("com.student.dealshare.mapper")
@ComponentScan(basePackages = {"com.student.dealshare"})
public class AdminApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdminApplication.class, args);
        System.out.println("========================================");
        System.out.println("大学生资源信息分享平台启动成功！");
        System.out.println("接口文档地址：http://localhost:8080/doc.html");
        System.out.println("Druid 监控地址：http://localhost:8080/druid/");
        System.out.println("========================================");
    }
}
