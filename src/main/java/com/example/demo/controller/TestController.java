package com.example.demo.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
public class TestController {

    @GetMapping("/csrf-token/publish")
    // (1) CSRF 토큰 발행은 CsrfFilter 에 의해 요청이 들어왔을때 생성되어 HttpSession 내 저장되어, ServletRequest 의 _csrf 속성으로 볼 수 있다.
    public ResponseEntity<String> publish(HttpServletRequest request) {
        CsrfToken token_received = (CsrfToken) request.getAttribute("_csrf");
        return ResponseEntity.ok(token_received.getToken());
    }

    @PostMapping("/csrf-token/usage")
    // (2) CSRF 토큰 사용은 CsrfFilter 에 의해 X-CSRF-TOKEN 헤더로 보내어진 토큰값을 앞서 HttpSession 내 저장되어있던 토큰과 비교하여 성공 시 Controller 로 온다.
    public ResponseEntity<String> usage(HttpServletRequest request) {
        return ResponseEntity.ok("POST : Hello, World!");
    }
}

