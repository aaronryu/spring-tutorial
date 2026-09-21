package com.example.demo.exception;

public class DatabaseException extends RuntimeException {
    public DatabaseException(String detail) {
        super("데이터베이스 접속에 실패했습니다 - 사유 : " + detail);
    }
}
