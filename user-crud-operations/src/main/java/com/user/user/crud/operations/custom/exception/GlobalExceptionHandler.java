package com.user.user.crud.operations.custom.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
   @ExceptionHandler({UserNotFoundException.class})
   public ResponseEntity<Map<String, Object>> handleUserNotFoundException(UserNotFoundException ex) {
      Map<String, Object> errorResponse = new HashMap();
      errorResponse.put("timestamp", LocalDateTime.now());
      errorResponse.put("status", HttpStatus.NOT_FOUND.value());
      errorResponse.put("error", "Not Found");
      errorResponse.put("message", ex.getMessage());
      return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
   }

   @ExceptionHandler({Exception.class})
   public ResponseEntity<Map<String, Object>> handleGeneralException(Exception ex) {
      Map<String, Object> errorResponse = new HashMap();
      errorResponse.put("timestamp", LocalDateTime.now());
      errorResponse.put("status", HttpStatus.INTERNAL_SERVER_ERROR.value());
      errorResponse.put("error", "Internal Server Error");
      errorResponse.put("message", ex.getMessage());
      return new ResponseEntity(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
   }
}
