package com.example.vms;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class CveApplication {
	public static void main(String[] args) {
        SpringApplication.run(CveApplication.class, args);
    }
}
