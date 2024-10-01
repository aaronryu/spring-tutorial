package com.example.demo.controller;

import com.example.demo.service.User;
import com.example.demo.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserServiceInterface userService;
    @Autowired
    private ApplicationContext applicationContext;

    @GetMapping("/bean")
    @ResponseBody
    public String bean() {
        return applicationContext.getBean(UserServiceInterface.class).toString();
    }

    @GetMapping("")
    public String userPage(Model model) {
        List<User> users = userService.findAll();
        model.addAttribute("members", users);
        return "/users/list";
    }

    @GetMapping("/1/detail")
    public String detailPage(Model model) {
        User user = userService.findById(1);
        model.addAttribute("id", user.getId());
        model.addAttribute("name", user.getName());
        model.addAttribute("age", user.getAge());
        model.addAttribute("job", user.getJob());
        model.addAttribute("specialty", user.getSpecialty());
        return "/users/detail";
    }

    @GetMapping("/1/data")
    @ResponseBody
    public User detailData() {
        User user = userService.findById(1);
        return user;
    }
}
