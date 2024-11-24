package com.example.demo.service;

import com.example.demo.controller.dto.MessageResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessageService {
    private final MessageRepository messageRepository;

    @Transactional
    public List<MessageResponseDto> findByUserId(Integer id) {
        List<Message> messages = messageRepository.findByUserId(id);
        return messages.stream().map(MessageResponseDto::from).toList();
    }
}
