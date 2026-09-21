package com.example.demo.exception.domain;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorType;

public class PaymentException extends CustomException {
    public PaymentException(ErrorType type) {
        super(type);
    }
}
