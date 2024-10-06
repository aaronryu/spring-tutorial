package com.example.demo.controller;

import com.example.demo.controller.dto.UserCreateRequestDto;
import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.controller.dto.common.BaseResponse;
import com.example.demo.exception.CustomException;
import com.example.demo.exception.ExceptionType;
import com.example.demo.service.IRepository;
import com.example.demo.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.spi.LoggingEventBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@Controller
@RequestMapping("/users")
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class UserController {
    UserService userService;

    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/bean")
    @ResponseBody
    public String bean() {
        return applicationContext.getBean(IRepository.class).toString();
    }

    @GetMapping("")
    public String userPage(Model model) {
        List<UserResponseDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "/users/list";
    }

    @GetMapping("/detail")
    public String detailPage(@RequestParam Integer id, Model model) {
        UserResponseDto user = userService.findById(id);
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        model.addAttribute("job", user.getJob());
        model.addAttribute("specialty", user.getSpecialty());
        return "/users/detail";
    }

    @GetMapping("/data")
    @ResponseBody
    public BaseResponse<UserResponseDto> detailData(@RequestParam Integer id) {
        try {
            UserResponseDto user = userService.findById(id);
            return BaseResponse.success(user);
        } catch (CustomException e) {
            log.warn(e.getMessage(), e);
            return BaseResponse.failure(e.getType());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResponse.failure(ExceptionType.UNCLASSIFIED_ERROR);
        }
    }

    @PostMapping("")
    @ResponseBody
    public BaseResponse<UserResponseDto> save(@RequestBody @Valid UserCreateRequestDto request) {
        try {
            UserResponseDto user = userService.save(request.getName(), request.getAge(), request.getJob(), request.getSpecialty());
            return BaseResponse.success(user);
        } catch (CustomException e) {
            log.warn(e.getMessage(), e);
            return BaseResponse.failure(e.getType());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return BaseResponse.failure(ExceptionType.UNCLASSIFIED_ERROR);
        }
    }
}
