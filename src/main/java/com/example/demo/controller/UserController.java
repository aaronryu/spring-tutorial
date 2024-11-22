package com.example.demo.controller;

import com.example.demo.controller.dto.UserCreateRequestDto;
import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.service.IUserService;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    IUserService userServiceJdkProxy;

    @GetMapping("")
    public ResponseEntity<List<UserResponseDto>> users() {
        log.info(userServiceJdkProxy.getClass().toString());
        List<UserResponseDto> users = userServiceJdkProxy.findAll();
        return ResponseEntity
//              .status(HttpStatusCode.valueOf(200))
                .status(HttpStatus.OK)      // 1. HTTP Status Code
                .body(users);               // 2. 결과 객체(List<User>)
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> user(@PathVariable Integer id) {
        log.info(userServiceJdkProxy.getClass().toString());
        UserResponseDto user = userServiceJdkProxy.findById(id);
        return ResponseEntity
//              .status(HttpStatusCode.valueOf(200))
                .status(HttpStatus.OK)      // 1. HTTP Status Code
                .body(user);                // 2. 결과 객체(User)
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDto> save(@RequestBody @Valid UserCreateRequestDto request) {
        log.info(userServiceJdkProxy.getClass().toString());
        UserResponseDto user = userServiceJdkProxy.save(request.getName(), request.getAge(), request.getJob(), request.getSpecialty());
        return ResponseEntity
//              .status(HttpStatusCode.valueOf(201))
                .status(HttpStatus.CREATED) // 1. HTTP Status Code
                .body(user);                // 2. 결과 객체(User)
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponseDto> update(@PathVariable Integer id, @RequestBody @Valid UserCreateRequestDto request) {
        log.info(userServiceJdkProxy.getClass().toString());
        UserResponseDto user = userServiceJdkProxy.update(id, request.getName(), request.getAge(), request.getJob(), request.getSpecialty());
        return ResponseEntity
//              .status(HttpStatusCode.valueOf(201))
                .status(HttpStatus.ACCEPTED) // 1. HTTP Status Code
                .body(user);                // 2. 결과 객체(User)
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Integer id) {
        log.info(userServiceJdkProxy.getClass().toString());
        userServiceJdkProxy.delete(id);
        return ResponseEntity
//              .status(HttpStatusCode.valueOf(201))
                .status(HttpStatus.ACCEPTED) // 1. HTTP Status Code
                .body(null);                // 2. 결과 객체(User)
    }
}
