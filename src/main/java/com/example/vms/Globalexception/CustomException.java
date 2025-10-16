package com.example.vms.Globalexception;

public class CustomException {

    // User not found
    public static class UserNotFoundException extends RuntimeException {
        public UserNotFoundException(String message) {
            super(message);
        }
    }

    // Duplicate email
    public static class EmailAlreadyExistsException extends RuntimeException {
        public EmailAlreadyExistsException(String message) {
            super(message);
        }
    }

    // Registration failed
    public static class RegistrationFailedException extends RuntimeException {
        public RegistrationFailedException(String message) {
            super(message);
        }
    }

    // Login failed
    public static class LoginFailedException extends RuntimeException {
        public LoginFailedException(String message) {
            super(message);
        }
    }

    public static class CveNotFoundException extends RuntimeException {
        public CveNotFoundException(String message) {
            super(message);
        }
    }

    public static class ProductNotFoundException extends RuntimeException {
        public ProductNotFoundException(String message) {
            super(message);
        }
    }
    public static class InvalidRequestException extends RuntimeException {
        public InvalidRequestException(String message) {
            super(message);
        }
    }

    public static class CveAlreadyExistsException extends RuntimeException {
        public CveAlreadyExistsException(String message) {
            super(message);
        }
    }
}
