package com.example.demo;

import lombok.extern.slf4j.Slf4j;

import java.sql.SQLException;

@Slf4j
public class DemoApplication {

    // 1개 계정밖에 허용되지 않는 데이터베이스 - username: "admin" + password: "1234"
    public static void connect(String username, String password) throws SQLException {
        if (username.equals("admin") && password.equals("1234")) {
            System.out.println("- 데이터베이스 접속 성공");
        } else {
            throw new SQLException("데이터베이스 접속 실패 - 사유 : ID / PW 일치하지 않음");
        }
    }

    public static void main(String[] args) {
        System.out.println(" - 프로그램이 시작되었습니다");
        try {
            connect("aaron", "7890");
        } catch (SQLException e) {
            log.error(e.getMessage());
        }
        System.out.println(" - 프로그램이 중간에 멈추지 않고, 정상적으로 종료되었습니다 = exit code 0");
    }
}
