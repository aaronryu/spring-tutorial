package com.example.demo.controller;

import com.example.demo.controller.dto.LoginRequestDto;
import com.example.demo.security.JwtProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtProvider jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequestDto dto){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.getUsername(),
                        dto.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String token = jwtProvider.generate(authentication);
        return ResponseEntity.ok(token);
    }
}
