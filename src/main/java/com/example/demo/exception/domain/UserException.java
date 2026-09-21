package com.example.demo.exception.domain;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorType;

public class UserException extends CustomException {
    public UserException(ErrorType type) {
      super(type);
    }
}
