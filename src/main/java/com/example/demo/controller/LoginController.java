package com.example.demo.controller;

import com.example.demo.controller.dto.LoginResponseDto;
import com.example.demo.service.LoginService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/database")
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST, value = "/login")
    public LoginResponseDto connect(@RequestParam String username, @RequestParam String password) {
        loginService.connect(username, password);
        return LoginResponseDto.success(username);
    }
}
