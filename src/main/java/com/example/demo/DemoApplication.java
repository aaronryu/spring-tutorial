package com.example.demo;

import com.example.demo.exception.CustomException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.util.StringUtils;

@Slf4j
@SpringBootApplication
public class DemoApplication {
    static void connect(String username, String password) {
        if (!StringUtils.hasLength(username)) {
            throw new CustomException("EMPTY_USERNAME", 2, "데이터베이스 접속에 필요한 Username 입력은 필수입니다.");
        }
        if (!StringUtils.hasLength(password)) {
            throw new CustomException("EMPTY_PASSWORD", 2, "데이터베이스 접속에 필요한 Password 입력은 필수입니다.");
        }
        if (!(username.equals("admin") && password.equals("1234"))) {
            throw new CustomException("LOGIN_FAILED", 1, "데이터베이스 접속 실패");
        }
        if (true/* 강제로 예외를 발생시키기 위해 if (true) 추가 - 그렇지않으면 '아래부터 실행되지 않는데 코드가 있다'는 빌드 오류 */) {
            throw new CustomException("CONNECTION_ERROR", 0, "Cannot connect to the Database Server");
        }
        log.info("- Database is connected successfully.");
    }

    public static void main(String[] args) {
        try {
            connect(null, "9876");
            connect("user", null);
            connect("user", "9876");
            connect("admin", "1234");
        } catch (CustomException e) {
            String message = String.format("[%s] (Priority: %s) %s", e.getType(), e.getPriority(), e.getMessage());
            switch (e.getPriority()) {
                case 0:
                    log.error(message, e);
                    break;
                case 1:
                case 2:
                default:
                    log.warn(message, e);
                    break;
            }
        }
    }
}
