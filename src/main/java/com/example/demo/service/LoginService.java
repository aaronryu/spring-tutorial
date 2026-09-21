package com.example.demo.service;

import com.example.demo.exception.CustomException;
import com.example.demo.exception.ErrorType;
import com.example.demo.exception.domain.UserException;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginService {

    // 1개 계정밖에 허용되지 않는 데이터베이스 - username: "admin" + password: "1234"
    public void connect(String username, String password) {
        if (true) {
            throw new RuntimeException("예상치 못한 갑작스러운 예외/오류 발생");
        }
        if (!StringUtils.hasLength(username)) {
            throw new CustomException(ErrorType.User.USERNAME_NOT_EXIST);
        }
        if (!StringUtils.hasLength(password)) {
            throw new CustomException(ErrorType.User.PASSWORD_NOT_EXIST);
        }
        if (!username.equals("admin") || !password.equals("1234")) {
            throw new CustomException(ErrorType.User.AUTHENTICATION_FAILED);
        }
        System.out.println("- 데이터베이스 접속 성공");
    }
}
