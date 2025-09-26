package com.example.RegisterAndLogin.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration // Ye batata hai ki ye class configuration ke liye use hogi
public class AppConfig {

    // ModelMapper ka ek bean banaya ja raha hai
    // Isse Spring Boot automatically manage karega aur jaha chahiye waha inject kar paoge
    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}
