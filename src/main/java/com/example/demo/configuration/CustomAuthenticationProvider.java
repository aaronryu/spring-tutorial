package com.example.demo.configuration;

import com.example.demo.repository.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomAuthenticationProvider implements AuthenticationProvider {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;
        String username = token.getName();
        String password = (String) token.getCredentials();

        User loggedUser = (User) userDetailsService.loadUserByUsername(username);
        if (!passwordEncoder.matches(password, loggedUser.getPassword())) {
            throw new BadCredentialsException(loggedUser.getUsername() + " : Invalid password");
        }
        return new UsernamePasswordAuthenticationToken(username, password, loggedUser.getAuthorities());
//      return new AuthenticationCredentialsNotFoundException("Error in AuthenticationProvider");
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
//      return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
//      - instanceOf 앞의 "객체"가 뒤의 클래스/인터페이스를 상속했는지 판단
//      - Class.isAssignableFrom 파라미터로 받은 "클래스"가 앞의 클래스/인터페이스를 상속했는지 판단
    }
}