package com.example.demo.controller.user;

import com.example.demo.controller.user.dto.UserCreateRequestDto;
import com.example.demo.controller.user.dto.UserResponseDto;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService service;

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    public ResponseEntity<UserResponseDto> user(@PathVariable Integer id) {
        UserResponseDto retrieved = service.findById(id);
        return ResponseEntity.ok(retrieved);
    }

    @RequestMapping(path = "", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponseDto>> user() {
        List<UserResponseDto> retrieved = service.findAll();
        return ResponseEntity.ok(retrieved);
    }

    @RequestMapping(path = "", method = RequestMethod.POST)
    public ResponseEntity<UserResponseDto> user(@RequestBody @Valid UserCreateRequestDto request) {
        UserResponseDto created = service.save(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(created);
    }
}
