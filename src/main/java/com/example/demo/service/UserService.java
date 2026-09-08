package com.example.demo.service;

import com.example.demo.controller.dto.UserCreateRequestDto;
import com.example.demo.controller.dto.UserResponseDto;
import com.example.demo.repository.*;
import com.example.demo.transaction.CustomTransaction;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionTemplate;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final DataSource dataSource;
    private final TransactionTemplate transactionTemplate;
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    public UserResponseDto findById(Integer id) {
        User retrievedUser = userRepository.findById(id).orElseThrow();
        List<Message> retrievedMessages = messageRepository.findByUserId(id).orElseThrow();
        return UserResponseDto.from(retrievedUser, retrievedMessages);
    }

    @CustomTransaction
    public UserResponseDto create(UserCreateRequestDto request) {
        User createdUser = userRepository.save(request.toCreating());
        Message createdMessages = messageRepository.save(Message.creating(createdUser.getName() + "님 회원가입 감사드립니다!", createdUser));
        return UserResponseDto.from(createdUser, Collections.singletonList(createdMessages));
    }
}
