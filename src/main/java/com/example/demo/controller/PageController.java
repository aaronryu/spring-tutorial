package com.example.demo.controller;

import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/")
@RequiredArgsConstructor
public class PageController {
    private final UserService userService;

    @GetMapping("")
    public String index() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(" - Logged User : " + authentication);
        return "index";
    }

    @GetMapping("/login")
    public String login() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(" - Anonymous User : " + authentication);
        return "login";
    }

    @GetMapping("/mypage")
    public String mypage(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(" - Logged User : " + authentication);
        String principle = (String) authentication.getPrincipal();
        model.addAttribute("username", principle);
        return "mypage/index";
    }

    @GetMapping("/admin")
    public String admin(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info(" - Logged User : " + authentication);
        List<UserResponseDto> users = userService.findAll();
        model.addAttribute("users", users);
        return "admin/index";
    }
}
