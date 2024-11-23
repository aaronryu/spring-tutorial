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
import org.springframework.transaction.support.DefaultTransactionDefinition;
import org.springframework.web.server.ResponseStatusException;

import javax.sql.DataSource;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService /* implements IUserService */ {
    private final UserRepository userRepository;
    private final MessageRepository messageRepository;
    private final UserJdbcApiDao userJdbcRepository;
    private final MessageJdbcApiDao messageJdbcRepository;
    private final UserJdbcTemplateDao userJdbcTemplateRepository;
    private final MessageJdbcTemplateDao messageJdbcTemplateRepository;

    private final DataSource dataSource;
    private final PlatformTransactionManager transactionManager;

    public UserResponseDto findById(Integer id) {
//      TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//      try {
//      User user = userJdbcTemplateRepository.findById(id);
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다. id : " + id));
//          transactionManager.commit(status);          // (A) Commit - 트랜잭션 추상화
        UserResponseDto result = UserResponseDto.from(user);
        return result;
//      } catch (Exception e) {
//          transactionManager.rollback(status);        // (B) Rollback - 트랜잭션 추상화
//          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "트랜잭션 수행 시 실패");
//      }
    }

    public List<UserResponseDto> findAll() {
        return userRepository.findAll()
                .stream()
                .map(UserResponseDto::from)
                .toList();
    }

    public UserResponseDto save(String name, Integer age, String job, String specialty) {
//      TransactionSynchronizationManager.initSynchronization();
//      Connection connection = DataSourceUtils.getConnection(dataSource);
//      PlatformTransactionManager transactionManager = new DataSourceTransactionManager(dataSource);
//      TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
//      try {
//          connection = dataSource.getConnection();    // Connection 생성
//          connection.setAutoCommit(false);            // Connection Auto-Commit 옵션 끄기
//      User user = userJdbcTemplateRepository.save(/* connection, */name, age, job, specialty);
//      List<Message> messages = messageJdbcTemplateRepository.save(/* connection, */user.getId(), user.getName() + "님 가입을 환영합니다.");
        User user = userRepository.save(new User(null, name, age, job, specialty, LocalDateTime.now()));
        messageRepository.save(new Message(null, user.getId(), user.getName() + "님 가입을 환영합니다.", LocalDateTime.now()));
        List<Message> messages = messageRepository.findByUserId(user.getId());
//          connection.commit();                        // (A) Commit
//          transactionManager.commit(status);          // (A) Commit - 트랜잭션 추상화
        UserResponseDto result = UserResponseDto.from(user);
        result.setMessages(messages);
        return result;
//      } catch (Exception e) {
//          try {
//              connection.rollback();                  // (B) Rollback
//          } catch (final SQLException ignored) {
//          }
//          transactionManager.rollback(status);        // (B) Rollback - 트랜잭션 추상화
//          throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "트랜잭션 수행 시 실패");
//      } finally {
//          DataSourceUtils.releaseConnection(connection, dataSource);
//      }
    }

    public UserResponseDto update(Integer id, String name, Integer age, String job, String specialty) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "유저 정보가 존재하지 않았습니다 - id : " + id));
        User param = user.updatedFrom(name, age, job, specialty);
        User updated = userRepository.save(param);
        return UserResponseDto.from(updated);
    }

    public void delete(Integer id) {
        userRepository.deleteById(id);
    }
}
