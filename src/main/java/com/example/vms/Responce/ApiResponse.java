package com.example.vms.Responce;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {
    private int status;
    private String message;
    private LocalDateTime timestamp = LocalDateTime.now();
    private T object;

    //  Constructor with object (success responses)
    public ApiResponse(int status, String message, T object) {
        this.status = status;
        this.message = message;
        this.object = object;
        this.timestamp = LocalDateTime.now();
    }

    //  Constructor without object (for delete or message-only responses)
    public ApiResponse(int status, String message) {
        this.status = status;
        this.message = message;
        this.timestamp = LocalDateTime.now();
    }
}
