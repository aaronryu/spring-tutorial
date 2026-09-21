package com.example.demo.exception.domain;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorType;

public class OrderException extends CustomException {
    public OrderException(ErrorType type) {
        super(type);
    }
}
