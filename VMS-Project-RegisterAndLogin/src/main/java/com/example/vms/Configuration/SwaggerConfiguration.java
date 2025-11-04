package com.example.vms.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI vmsOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("\uD83D\uDEE1\uFE0F Vulnerability Management System API")
                        .description("""
                                Welcome to the **VMS REST API** documentation.<br>
                                Use this UI to explore, test, and understand all the available endpoints.<br><br>
                                ✨ Features:<br>
                                - User Registration & Login<br>
                                - OTP Email Verification<br>
                                - CVE & Product Management<br>
                                - Employee Management<br>
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Ashish")
                                .email("ashishkumarrr2007@gmail.com")
                                .url("https://github.com/ashishkr-code/VMS-Project.git"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT"))
                );
    }
}
