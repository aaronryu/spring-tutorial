package com.example.demo.service;

import com.example.demo.controller.dto.UserResponseDto;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public UserResponseDto findById(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. id : " + id));
        UserResponseDto result = UserResponseDto.from(user);
        return result;
    }

    @Transactional
    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::from)
                .toList();
    }

    @Transactional
    public UserResponseDto save(String name, Integer age, String job, String specialty) {
        User createdUser = userRepository.save(User.from(name, age, job, specialty));
        Message registrationMessage = messageRepository.save(Message.from(createdUser.getId(), createdUser.getName() + "님 가입을 환영합니다."));
        createdUser.addMessage(registrationMessage);
        return UserResponseDto.from(createdUser);
    }

    @Transactional
    public UserResponseDto update(Integer id, String name, Integer age, String job, String specialty) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 정보가 존재하지 않았습니다 - id : " + id));
        User param = user.updatedFrom(name, age, job, specialty);
        User updated = userRepository.save(param);
        return UserResponseDto.from(updated);
    }

    @Transactional
    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
