package com.example.vms.Utility;

import com.example.vms.Globalexception.CustomException;
import com.example.vms.Model.Auditor;
import com.example.vms.Repository.AuditorRepository;

public class RegistrationUtil {

    // Check if passwords match
    public static void validatePasswords(String password, String confirmPassword) {
        if (!password.equals(confirmPassword)) {
            throw new CustomException.RegistrationFailedException("Passwords do not match");
        }
    }

    // Check if email already exists
    public static void validateEmail(String email, AuditorRepository auditorRepository) {
        if (auditorRepository.existsByEmail(email)) {
            throw new CustomException.EmailAlreadyExistsException("Email is already in use");
        }
    }

    // Optional: Full validation method
    public static void validateRegistration(String email, String password, String confirmPassword, AuditorRepository auditorRepository) {
        validatePasswords(password, confirmPassword);
        validateEmail(email, auditorRepository);
    }
}
