package com.example.vms.UIService;

import com.example.vms.Model.Auditor;
import com.example.vms.Repository.AuditorRepository;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Service
public class ForgetPassword {

    @Autowired
    private AuditorRepository userRepo;

    @Autowired
    private JavaMailSender mailSender;

    // Temporary in-memory token store (use DB or Redis in production)
    private final Map<String, String> tokenStore = new HashMap<>();

    /**
     * Generate a reset token, store it temporarily, and send a reset email.
     */
    public void generateResetToken(String email) {
        Optional<Auditor> userOpt = userRepo.findByEmail(email);
        if (userOpt.isEmpty()) {
            System.out.println("No user found with email: " + email);
            return;
        }

        // Generate token and store
        String token = UUID.randomUUID().toString();
        tokenStore.put(token, email);
        String resetLink = "http://localhost:8087/reset-password?token=" + token;

        try {
            sendResetEmail(email, resetLink);
            System.out.println("✅ Password reset email sent successfully to: " + email);
        } catch (MessagingException e) {
            System.err.println("❌ Failed to send reset email: " + e.getMessage());
        }
    }

    /**
     * Send a styled HTML email with the reset link.
     */
    private void sendResetEmail(String toEmail, String resetLink) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

        helper.setTo(toEmail);
        helper.setSubject("❄️ Reset Your VMS Password | Winter Security Alert ❄️");

        String htmlTemplate = """
                <!DOCTYPE html>
                <html>
                <head>
                    <meta charset="UTF-8">
                    <title>Reset Your VMS Password</title>
                    <style>
                        body {
                            font-family: 'Poppins', sans-serif;
                            background: linear-gradient(135deg, #cbe6ef, #f8fbff);
                            color: #2c3e50;
                            margin: 0;
                            padding: 0;
                        }
                        .container {
                            max-width: 600px;
                            margin: 40px auto;
                            background: #ffffff;
                            border-radius: 15px;
                            box-shadow: 0 8px 20px rgba(0, 0, 0, 0.1);
                            padding: 30px;
                            text-align: center;
                        }
                        h2 {
                            color: #1abc9c;
                        }
                        p {
                            font-size: 16px;
                            color: #555;
                            margin: 20px 0;
                        }
                        .reset-btn {
                            display: inline-block;
                            background: linear-gradient(90deg, #1abc9c, #16a085);
                            color: #fff;
                            padding: 12px 25px;
                            border-radius: 30px;
                            text-decoration: none;
                            font-weight: 600;
                            margin-top: 20px;
                            box-shadow: 0 4px 10px rgba(26, 188, 156, 0.3);
                        }
                        .reset-btn:hover {
                            background: linear-gradient(90deg, #16a085, #1abc9c);
                        }
                        .footer {
                            margin-top: 30px;
                            font-size: 13px;
                            color: #888;
                        }
                    </style>
                </head>
                <body>
                    <div class="container">
                        <h2>❄️ VMS Password Reset ❄️</h2>
                        <p>We received a request to reset your password.</p>
                        <p>Click the button below to securely reset your password:</p>
                        <a href="${resetLink}" class="reset-btn">Reset My Password</a>
                        <p class="footer">If you didn’t request this, you can safely ignore this email.<br>
                        © VMS Team - Keeping your access secure.</p>
                    </div>
                </body>
                </html>
                """;

        // Replace placeholder with actual link
        String htmlContent = htmlTemplate.replace("${resetLink}", resetLink);
        helper.setText(htmlContent, true);

        mailSender.send(message);
    }

    /**
     * Validate whether a reset token exists.
     */
    public boolean isValidToken(String token) {
        return tokenStore.containsKey(token);
    }

    /**
     * Reset the user’s password using the valid token.
     */
    public boolean resetPassword(String token, String newPassword) {
        String email = tokenStore.get(token);
        if (email == null) return false;

        Optional<Auditor> userOpt = userRepo.findByEmail(email);
        if (userOpt.isPresent()) {
            Auditor user = userOpt.get();
            user.setPassword(newPassword); // TODO: encode password in real app
            userRepo.save(user);
            tokenStore.remove(token);
            return true;
        }
        return false;
    }
}
