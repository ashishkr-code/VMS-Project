package com.example.RegisterAndLogin.Controller;

import com.example.RegisterAndLogin.Dto.LoginRequest;
import com.example.RegisterAndLogin.Dto.LoginResponse;
import com.example.RegisterAndLogin.Dto.RegisterRequest;
import com.example.RegisterAndLogin.Dto.RegisterResponse;

import com.example.RegisterAndLogin.Service.AuditorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auditor")
public class AuditorController {

    @Autowired
    private AuditorService auditorService;

    //  Register API
    @PostMapping("/register")
    public RegisterResponse register(@Valid @RequestBody RegisterRequest request) {
        return auditorService.register(request);
    }

    //  Login API
    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        return auditorService.login(request);
    }

    @DeleteMapping("/{id}")
    public String deleteAuditor(@PathVariable Long id){
        return auditorService.deleteAuditor(id);
    }
}
