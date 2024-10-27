package com.example.demo.controller;

import com.example.demo.controller.dto.UserCreateRequestDto;
import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    @GetMapping("")
    public ResponseEntity<List<UserResponseDto>> users() {
        try {
            List<UserResponseDto> users = userService.findAll();
            return ResponseEntity
//                  .status(HttpStatusCode.valueOf(200))
                    .status(HttpStatus.OK)      // 1. HTTP Status Code
                    .body(users);               // 2. 결과 객체(List<User>)
        } catch (RuntimeException e) {
            return ResponseEntity
//                  .status(HttpStatusCode.valueOf(404))
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDto> user(@PathVariable Integer id) {
        try {
            UserResponseDto user = userService.findById(id);
            return ResponseEntity
//                  .status(HttpStatusCode.valueOf(200))
                    .status(HttpStatus.OK)      // 1. HTTP Status Code
                    .body(user);                // 2. 결과 객체(User)
        } catch (RuntimeException e) {
            return ResponseEntity
//                  .status(HttpStatusCode.valueOf(404))
                    .status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
    }

    @PostMapping("")
    public ResponseEntity<UserResponseDto> save(@RequestBody @Valid UserCreateRequestDto request) {
        UserResponseDto user = userService.save(request.getName(), request.getAge(), request.getJob(), request.getSpecialty());
        return ResponseEntity
//              .status(HttpStatusCode.valueOf(201))
                .status(HttpStatus.CREATED) // 1. HTTP Status Code
                .body(user);                // 2. 결과 객체(User)
    }
}
