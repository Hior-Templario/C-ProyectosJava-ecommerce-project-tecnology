package com.development.ecommerce_tecnology.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

@RestControllerAdvice
public class GlobalExceptionHandler {


    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Map<String, Object>> handleResponseStatusException(
       ResponseStatusException ex,
       HttpServletRequest request
    ) {
        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status" , ex.getStatusCode().value());
        errorBody.put("error", ex.getReason());
        errorBody.put("message", ex.getReason());
        errorBody.put("path" , request.getRequestURL());
        errorBody.put("method" , request.getMethod());

        return new ResponseEntity<>(errorBody, ex.getStatusCode());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, Object>> handleValidationExceptions(
            MethodArgumentNotValidException ex){
        Map<String, String> fieldErrors = new HashMap<>();
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            fieldErrors.put(error.getField(), error.getDefaultMessage());
        });

        Map<String, Object> errorBody = new LinkedHashMap<>();
        errorBody.put("timestamp", LocalDateTime.now());
        errorBody.put("status" , HttpStatus.BAD_REQUEST.value());
        errorBody.put("error", "Validacion fallida");
        errorBody.put("message", "Uno o más campos no son válidos");
        errorBody.put("errors" , fieldErrors);

        return new ResponseEntity<>(errorBody, HttpStatus.BAD_REQUEST);
    }

}
