package com.campusecohub;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 校园生态中心应用主类
 */
@MapperScan("com.campusecohub.mapper")
@SpringBootApplication
public class CampusEcoHubApplication {
    public static void main(String[] args) {
        SpringApplication.run(CampusEcoHubApplication.class, args);
    }
}
