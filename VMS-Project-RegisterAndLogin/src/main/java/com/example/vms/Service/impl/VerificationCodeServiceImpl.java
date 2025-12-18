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

        String baseUrl = System.getenv("APP_VERIFICATION_BASE_URL");
        if (baseUrl == null) {
            baseUrl = "http://localhost:8087/api/verify";
        }
        // Verification link
        String verificationLink = "http://localhost:8087/api/verify?email=" + email + "&token=" + otp;

        // ❄️ Winter-Themed HTML Email ❄️
        String subject = "❄️ Winter Greetings! Verify Your VMS Account ☃️";
        String htmlBody = """
                <div style="font-family: 'Segoe UI', Arial, sans-serif; background: linear-gradient(135deg, #e0f7ff, #f8fdff); 
                            padding: 25px; border-radius: 12px; border: 2px solid #b3e5fc; max-width: 600px; margin: auto; 
                            box-shadow: 0 0 15px rgba(100,180,255,0.4);">
                    
                    <h2 style="color:#0077b6; text-align:center;">☃️ Welcome to VMS — Winter Edition ❄️</h2>
                    
                    <p>Hi <b>%s</b>,</p>
                    <p>We’re delighted to have you join the <b>Vulnerability Management System</b> community!</p>
                    <p>Before we start securing together, please verify your email by clicking the button below:</p>
                    
                    <div style="text-align:center; margin:30px 0;">
                        <a href="%s" style="background: linear-gradient(45deg, #00b4d8, #90e0ef); color:white; 
                           padding:14px 30px; text-decoration:none; border-radius:10px; font-weight:bold; 
                           font-size:16px; box-shadow:0 0 10px rgba(0,180,216,0.4);">
                            🔒 Verify My Account
                        </a>
                    </div>
                    
                    <p>Your One-Time Password (OTP): 
                        <span style="background:#caf0f8; color:#0077b6; padding:5px 10px; border-radius:6px; font-weight:bold;">
                            %d
                        </span>
                    </p>
                    <p>This code will expire in <b>10 minutes</b>.</p>
                    
                    <hr style="border:none; border-top:1px solid #cce7ff; margin:25px 0;">
                    
                    <p style="color:#4d4d4d; text-align:center;">
                        Stay safe, stay warm, and keep your systems secure this winter! ❄️<br>
                        <b>— Team VMS</b>
                    </p>
                </div>
                """.formatted(auditor.getUsername(), verificationLink, otp);

        emailService.sendHtmlEmail(email, subject, htmlBody);
        return "Winter verification email sent to: " + email;
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
