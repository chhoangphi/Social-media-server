package com.social.net;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.social.net.*"})
@EntityScan(basePackages = {"com.social.net.*"})
@EnableJpaRepositories(basePackages = {"com.social.net.*"})
public class SocialMediaServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(SocialMediaServerApplication.class, args);
    }

}
