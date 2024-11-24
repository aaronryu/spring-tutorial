package com.example.demo.service;

import java.util.List;

public interface UserRepositoryCustom {
    List<User> findByName(String name);
}