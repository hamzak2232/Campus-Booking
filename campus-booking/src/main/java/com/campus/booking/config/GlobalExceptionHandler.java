package com.campus.booking.config;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;
import jakarta.persistence.EntityNotFoundException;

import java.time.Instant;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 404 Not Found
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Map<String, Object>> handleNotFound(EntityNotFoundException ex) {
        Map<String, Object> error = Map.of(
                "timestamp", Instant.now(),
                "status", 404,
                "error", "Not Found",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    // 400 Bad Request
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, Object>> handleBadRequest(IllegalArgumentException ex) {
        Map<String, Object> error = Map.of(
                "timestamp", Instant.now(),
                "status", 400,
                "error", "Bad Request",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    // 403 Forbidden
    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Map<String, Object>> handleAccessDenied(AccessDeniedException ex) {
        Map<String, Object> error = Map.of(
                "timestamp", Instant.now(),
                "status", 403,
                "error", "Forbidden",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.FORBIDDEN).body(error);
    }

    // 500 Internal Server Error (catch-all)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Map<String, Object>> handleGeneral(Exception ex) {
        Map<String, Object> error = Map.of(
                "timestamp", Instant.now(),
                "status", 500,
                "error", "Internal Server Error",
                "message", ex.getMessage()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }
}
