package com.example.vms.Service;

public interface VerificationCodeService {
    String sendVerificationCode(String email);
    String verifyCode(int token, String email);
}
