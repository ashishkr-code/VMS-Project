package com.example.CVE.Globalexception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestControllerAdvice // Ye batata hai ki ye class saare controllers ke liye exception handle karegi
public class Globalexception {

    // Ye method validation errors (jaise @NotBlank, @Min, @Size fail ho jaye) ko handle karega
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationErrors(MethodArgumentNotValidException ex) {
        // Map banate hain jisme field name aur uska error message store hoga
        Map<String, String> errors = new HashMap<>();

        // Validation errors ko iterate karke map me dal dete hain
        ex.getBindingResult().getFieldErrors()
                .forEach(error -> errors.put(error.getField(), error.getDefaultMessage()));

        // 400 (Bad Request) ke saath response bhejna
        return ResponseEntity.badRequest().body(errors);
    }

    // Ye method NoSuchElementException ko handle karega
    // e.g., jab kisi employee ki id se record nahi milta
    @ExceptionHandler(NoSuchElementException.class)
    public ResponseEntity<Map<String, String>> handleNotFound(NoSuchElementException ex) {
        // Error message ko map me dalna
        Map<String, String> error = new HashMap<>();
        error.put("error", ex.getMessage());

        // 404 (Not Found) ke saath response bhejna
        return ResponseEntity.status(404).body(error);
    }
}
