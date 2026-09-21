package com.example.demo.controller;

import com.example.demo.controller.dto.LoginResponseDto;
import com.example.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@RequestMapping("/api/database")
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @ResponseBody
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public ResponseEntity<LoginResponseDto> connect(@RequestParam String username, @RequestParam String password) {
        loginService.connect(username, password);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(LoginResponseDto.success(username));
    }
}
