package com.example.demo.configuration;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable/* = (auth) -> auth.disable() */);
        http.httpBasic(AbstractHttpConfigurer::disable/* = (auth) -> auth.disable() */);
        http.csrf(AbstractHttpConfigurer::disable/* = (auth) -> auth.disable() */);

//      http.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN"));
//      http.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/*")).hasRole());
        http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
//      http.formLogin(Customizer.withDefaults())
        http.formLogin(form -> form.loginPage("/login").permitAll());

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .clearAuthentication(true)
//              .invalidateHttpSession(true)
//              .addLogoutHandler((request, response, authentication) -> {
//                  HttpSession session = request.getSession();
//                  session.invalidate();
//              })
        );
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
        UserDetails aaron = User.withUsername("aaron").password(passwordEncoder.encode("456")).roles("USER", "ADMIN").build();
        UserDetails baron = User.withUsername("baron").password(passwordEncoder.encode("456")).roles("USER").build();
        UserDetails caron = User.withUsername("caron").password(passwordEncoder.encode("456")).roles("USER").build();
        return new InMemoryUserDetailsManager(aaron, baron, caron);
    }
}
