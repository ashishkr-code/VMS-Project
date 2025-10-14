package com.example.vms.Configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Userconfiguration {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }
}