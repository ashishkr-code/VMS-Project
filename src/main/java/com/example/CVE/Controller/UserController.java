package com.example.CVE.Controller;

import com.example.CVE.Dto.CveResponse;
import com.example.CVE.Service.UserService; // UserService ko import karein
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user/cves")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<CveResponse>> getAllCves() {
        List<CveResponse> cves = userService.getAllCves();
        return new ResponseEntity<>(cves, HttpStatus.OK);
    }
}