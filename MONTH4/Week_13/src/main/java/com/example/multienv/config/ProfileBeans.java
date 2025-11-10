package com.example.multienv.config;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
@Configuration
public class ProfileBeans {
    @Bean
    @Profile("dev")
    public String devBean() {
        return "dev";
    }
    @Bean
    @Profile("prod")
    public String prodBean() {
        return "prod";
    }
}
