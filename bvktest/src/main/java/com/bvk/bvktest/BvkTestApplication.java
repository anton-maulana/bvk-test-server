package com.bvk.bvktest;

import com.bvk.bvktest.config.AppProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(AppProperties.class)
public class BvkTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(BvkTestApplication.class, args);
    }
}
