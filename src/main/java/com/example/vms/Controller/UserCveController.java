package com.example.vms.Controller;

import com.example.vms.Dto.CveResponse;
import com.example.vms.Service.UserService; // UserService ko import karein
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user/cves")
@Tag(name = "User", description = "Endpoints for user can see cves")
public class UserCveController {

    @Autowired
    private UserService userService;


    @GetMapping
    public ResponseEntity<List<CveResponse>> getAllCves() {
        List<CveResponse> cves = userService.getAllCves();
        return new ResponseEntity<>(cves, HttpStatus.OK);
    }
}