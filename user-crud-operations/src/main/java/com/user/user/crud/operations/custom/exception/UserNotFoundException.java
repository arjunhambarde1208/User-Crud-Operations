package com.user.user.crud.operations.custom.exception;

public class UserNotFoundException extends RuntimeException {
   public UserNotFoundException(String message) {
      super(message);
   }
}
