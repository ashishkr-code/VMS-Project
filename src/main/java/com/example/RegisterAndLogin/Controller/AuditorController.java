package com.example.RegisterAndLogin.Controller;

import com.example.RegisterAndLogin.Dto.LoginRequest;
import com.example.RegisterAndLogin.Dto.RegisterRequest;
import com.example.RegisterAndLogin.Service.AuditorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auditor")
public class AuditorController {

    @Autowired
    private AuditorService auditorService;

    //  Register API
    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody RegisterRequest request) {
        return auditorService.register(request);
    }

    //  Login API
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {
        return auditorService.login(request);
    }
}
