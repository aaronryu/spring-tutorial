package com.example.demo.service;

import com.example.demo.controller.dto.UserResponseDto;

import java.util.List;

public interface IUserService {
    public abstract UserResponseDto findById(Integer id);

    public abstract List<UserResponseDto> findAll();

    public abstract UserResponseDto save(String name, Integer age, String job, String specialty);

    public abstract UserResponseDto update(Integer id, String name, Integer age, String job, String specialty);

    public abstract void delete(Integer id);
}
