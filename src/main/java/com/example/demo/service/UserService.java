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
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;

    @Transactional
    public UserResponseDto findById(Integer id) {
        User retrievedUser = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. id : " + id));
        UserResponseDto result = UserResponseDto.from(retrievedUser);
        return result;
    }

    @Transactional
    public List<UserResponseDto> findAll() {
        List<User> users = userRepository.findAll();
        return users
                .stream()
                .map(UserResponseDto::from)
                .toList();
    }

    @Transactional
    public UserResponseDto save(String name, Integer age, String job, String specialty) {
        User createdUser = userRepository.save(User.from(name, age, job, specialty));
        Message message = messageRepository.save(Message.from(createdUser.getId(), createdUser.getName() + "님 가입을 환영합니다."));
        createdUser.addMessage(message);
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
//      userRepository.deleteById(id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 정보가 존재하지 않았습니다 - id : " + id));
        userRepository.delete(user);
    }

    @Transactional
    public void deleteMessages(Integer id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 정보가 존재하지 않았습니다 - id : " + id));
//      user.setMessages(Collections.emptyList());      // 기존 Collection 에 대해서 참조(Reference, 주소)를 아예 지워버리는것이아니라 = cascade="all-delete-orphan" 불가
        List<Message> messages = user.getMessages();    // 기존 Collection 이 갖는 참조(Reference, 주소)는 유지한채 그 내부의 값만 없애야한다.
        messages.clear();
//      user.setMessages(messages);                     // 이 구문은 굳이 필요는 없다. 어자피 user 는 messages 를 참조하고있었으니
    }
}
