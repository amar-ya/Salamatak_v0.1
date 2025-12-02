package org.example.salamatak_v01.Advice;

import org.example.salamatak_v01.Api.ApiException;
import org.example.salamatak_v01.Api.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    @ExceptionHandler(value = ApiException.class)
    public ResponseEntity<?> apiException(ApiException ex){
        return ResponseEntity.status(400).body(new ApiResponse(ex.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<?> methodArgumentNotValidException(MethodArgumentNotValidException ex){
        return ResponseEntity.status(400).body(new ApiResponse(ex.getFieldError().getDefaultMessage()));
    }
}
