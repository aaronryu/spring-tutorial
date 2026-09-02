package com.example.demo.controller;

import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @RequestMapping(method = RequestMethod.GET, value = "/{id}")
    public UserResponseDto retrieve(@PathVariable @NonNull Integer id) throws SQLException {
        return userService.findById(id);
    }
}
