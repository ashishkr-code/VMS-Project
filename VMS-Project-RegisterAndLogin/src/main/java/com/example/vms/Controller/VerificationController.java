package com.example.vms.Controller;

import com.example.vms.Responce.ApiResponse;
import com.example.vms.Service.VerificationCodeService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@Tag(name = "Verification", description = "Endpoints for email/OTP verification")
public class VerificationController {

    @Autowired
    private VerificationCodeService verificationCodeService;

    @GetMapping("/verify")
    public ResponseEntity<ApiResponse<String>> verifyUser(
            @RequestParam("token") int token,
            @RequestParam("email") String email
    ) {
        // Verification logic
        String verifiedEmail = verificationCodeService.verifyCode(token, email);

        // Return structured response
        ApiResponse<String> response = new ApiResponse<>(
                HttpStatus.OK.value(),
                "Email verified successfully",
                verifiedEmail
        );

        return ResponseEntity.ok(response);
    }
}
