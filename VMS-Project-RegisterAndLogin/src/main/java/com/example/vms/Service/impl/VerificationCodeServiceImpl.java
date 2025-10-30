package com.example.vms.Service.impl;

import com.example.vms.Model.Auditor;
import com.example.vms.Model.VerificationCode;
import com.example.vms.Repository.AuditorRepository;
import com.example.vms.Repository.VerificationCodeRepository;
import com.example.vms.Service.VerificationCodeService;
import com.example.vms.Utility.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class VerificationCodeServiceImpl implements VerificationCodeService {

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @Autowired
    private AuditorRepository auditorRepository;

    @Autowired
    private EmailService emailService;

    @Override
    public String sendVerificationCode(String email) {
        Auditor auditor = auditorRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Auditor not found with email: " + email));

        // Delete old OTPs
        verificationCodeRepository.deleteAllByAuditor(auditor);

        // Generate OTP
        int otp = new Random().nextInt(900000) + 100000;

        // Save OTP
        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setToken(otp);
        verificationCode.setAuditor(auditor);
        verificationCode.setExpireDate(LocalDateTime.now().plusMinutes(10));
        verificationCodeRepository.save(verificationCode);

        // Verification link
        String verificationLink = "http://localhost:8087/api/verify?email=" + email + "&token=" + otp;

        // Diwali themed HTML
        String subject = "🪔 Diwali Greetings! Verify Your VMS Account 🪔";
        String htmlBody = """
                <div style="font-family: Arial, sans-serif; background-color: #fff8e7; padding: 25px; border-radius: 12px; border: 2px solid #ffb84d; max-width: 600px; margin: auto; box-shadow: 0 0 15px rgba(255,150,0,0.4);">
                    <h2 style="color:#ff6600; text-align:center;">✨ Happy Diwali & Welcome to VMS! ✨</h2>
                    <p>Dear <b>%s</b>,</p>
                    <p>We’re thrilled to have you join <b>Vulnerability Management System</b>! 🎉</p>
                    <p>To complete your registration, please verify your email by clicking the button below:</p>
                    <div style="text-align:center; margin:30px 0;">
                        <a href="%s" style="background: linear-gradient(45deg, #ff6600, #ffcc00); color:white; padding:14px 30px; text-decoration:none; border-radius:10px; font-weight:bold; font-size:16px;">
                            🔒 Click Here to Verify
                        </a>
                    </div>
                    <p>Your OTP: <b>%d</b></p>
                    <p>Expires in 10 minutes.</p>
                    <p style="color:#888;">Wishing you a safe & secure Diwali! 🪔✨</p>
                </div>
                """.formatted(auditor.getUsername(), verificationLink, otp);

        emailService.sendHtmlEmail(email, subject, htmlBody);
        return "Festive verification email sent to: " + email;
    }

    @Override
    public String verifyCode(int token, String email) {
        VerificationCode verificationCode = verificationCodeRepository
                .findByTokenAndAuditorEmail(token, email)
                .orElseThrow(() -> new RuntimeException("Invalid or expired verification code."));

        if (verificationCode.getExpireDate().isBefore(LocalDateTime.now())) {
            return "❌ Verification link expired! Request a new one.";
        }

        Auditor auditor = verificationCode.getAuditor();
        auditor.setVerfied(true);
        auditorRepository.save(auditor);

        return "🎉 Email verified successfully for: " + auditor.getEmail();
    }
}
