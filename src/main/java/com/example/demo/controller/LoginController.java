package com.example.demo.controller;

import com.example.demo.controller.dto.LoginRequestDto;
import com.example.demo.controller.dto.LoginResponseDto;
import com.example.demo.service.LoginService;
import com.example.demo.validation.OnLogin;
import com.example.demo.validation.OnRegistration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequestMapping("/api/database")
@Controller
@RequiredArgsConstructor
public class LoginController {
    private final LoginService loginService;

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST, value = "/login", consumes = "!application/json")
//  public LoginResponseDto connect(@RequestParam @Size(min = 4) String username, @RequestParam @Size(min = 4) String password) {
    public LoginResponseDto<Void> connect(@RequestParam String username, @RequestParam String password) {
        loginService.connect(username, password);
        return LoginResponseDto.success(username);
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST, value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
//  public LoginResponseDto connect(@RequestBody @Valid LoginRequestDto request) {
    public LoginResponseDto<Void> connect(@RequestBody @Validated(OnLogin.class) LoginRequestDto request) {
        loginService.connect(request);
        return LoginResponseDto.success(request.getUsername());
    }

    @ResponseBody
    @ResponseStatus(HttpStatus.ACCEPTED)
    @RequestMapping(method = RequestMethod.POST, value = "/registration")
    public LoginResponseDto<Void> registration(@RequestBody @Validated(OnRegistration.class) LoginRequestDto request) {
//      loginService.registration(request); - 실제 회원가입 로직에 의해 회원가입이 성공적으로 되었다고 가정
        return LoginResponseDto.success(request.getUsername());
    }
}
