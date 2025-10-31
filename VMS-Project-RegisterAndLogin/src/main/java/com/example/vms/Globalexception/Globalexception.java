package com.example.vms.Globalexception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice  // Ye batata hai ki ye class saare controllers ke liye exceptions handle karegi
public class Globalexception {

    //  COMMON RESPONSE STRUCTURE BANAYE
    private ResponseEntity<Map<String, Object>> buildResponse(HttpStatus status, String message) {
        Map<String, Object> response = new HashMap<>();
        response.put("timestamp", LocalDateTime.now());
        response.put("status", status.value());
        response.put("error", status.getReasonPhrase());
        response.put("message", message);
        return ResponseEntity.status(status).body(response);
    }

    //  Validation Errors (@NotBlank, @Size, etc.)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));
        return ResponseEntity.badRequest().body(errors); // 400 Bad Request
    }

    //  Jab koi record nahi milta (Optional.empty / findById fail)
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(NoSuchElementException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  User Not Found Exception
    @ExceptionHandler(CustomException.UserNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleUserNotFound(CustomException.UserNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  Email Already Exists Exception
    @ExceptionHandler(CustomException.EmailAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleDuplicateEmail(CustomException.EmailAlreadyExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }

    // ️ Registration Failed Exception
    @ExceptionHandler(CustomException.RegistrationFailedException.class)
    public ResponseEntity<Map<String, Object>> handleRegistrationFailed(CustomException.RegistrationFailedException ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex.getMessage());
    }

    // ️ Login Failed Exception
    @ExceptionHandler(CustomException.LoginFailedException.class)
    public ResponseEntity<Map<String, Object>> handleLoginFailed(CustomException.LoginFailedException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    //  CVE Not Found
    @ExceptionHandler(CustomException.CveNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleCveNotFound(CustomException.CveNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  Product Not Found
    @ExceptionHandler(CustomException.ProductNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleProductNotFound(CustomException.ProductNotFoundException ex) {
        return buildResponse(HttpStatus.NOT_FOUND, ex.getMessage());
    }

    //  CVE Already Exists
    @ExceptionHandler(CustomException.CveAlreadyExistsException.class)
    public ResponseEntity<Map<String, Object>> handleCveAlreadyExists(CustomException.CveAlreadyExistsException ex) {
        return buildResponse(HttpStatus.CONFLICT, ex.getMessage());
    }
    @ExceptionHandler(CustomException.InvalidRequestException.class)
    public ResponseEntity<Map<String, Object>> InvalidRequestException(CustomException.InvalidRequestException ex) {
        return buildResponse(HttpStatus.BAD_REQUEST, ex.getMessage());
    }

    //  Fallback — Agar koi unknown error aa gaya to ye catch karega
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGlobalException(Exception ex) {
        return buildResponse(HttpStatus.INTERNAL_SERVER_ERROR, "Unexpected error: " + ex.getMessage());
    }
}
