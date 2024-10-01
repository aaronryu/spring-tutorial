package com.example.demo.controller;

import com.example.demo.service.User;
import com.example.demo.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private List<UserServiceInterface> userServices;

    @GetMapping("")
    public ModelAndView userPage() {
        ModelAndView modelAndView = new ModelAndView();
        userServices.forEach((each) -> {
            System.out.println(each.getClass().getSimpleName());
        });
        UserServiceInterface AUserService = userServices.get(0);
        List<User> users = AUserService.findAll();
        modelAndView.addObject("members", users);
        modelAndView.setViewName("/users/list");
        return modelAndView;
    }

    @GetMapping("/1/detail")
    public ModelAndView detailPage() {
        ModelAndView modelAndView = new ModelAndView();
        userServices.forEach((each) -> {
            System.out.println(each.getClass().getSimpleName());
        });
        UserServiceInterface AUserService = userServices.get(0);
        User user = AUserService.findById(1);
        modelAndView.addObject("id", user.getId());
        modelAndView.addObject("name", user.getName());
        modelAndView.addObject("age", user.getAge());
        modelAndView.addObject("job", user.getJob());
        modelAndView.addObject("specialty", user.getSpecialty());
        modelAndView.setViewName("/users/detail");
        return modelAndView;
    }
}
