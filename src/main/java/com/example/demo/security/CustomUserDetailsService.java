//package com.example.demo.security;
//
//import com.example.demo.repository.UserRepository;
//import com.example.demo.repository.entity.User;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
///**
// * 가장 이상적인건 사실
// * - UserDetails implements 해서 User 라는 Entity Class 를 정의하여 우리가 사용하려는 Class 와 Security 목적을 동시에 이룬것처럼
// * - UserDetailsService implements 해서 UserService 라는 @Service Class 를 정의하여 우리가 사용하려는 Serive 와 Security 목적을 동시에 이루는것
// */
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//    private final UserRepository userRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        User retrieved = userRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("존재하지 않는 유저입니다 - username : " + username));
//        return retrieved;
//    }
//}