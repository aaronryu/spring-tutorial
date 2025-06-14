package com.example.demo.configuration;

import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfigurationSource;

@Configuration
@EnableWebSecurity(debug = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomAuthenticationProvider authenticationProvider;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomLogoutSuccessHandler logoutSuccessHandler;
    private final CorsConfigurationSource reactConfigurationSource;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.formLogin(AbstractHttpConfigurer::disable/* = (auth) -> auth.disable() */);
        http.httpBasic(AbstractHttpConfigurer::disable/* = (auth) -> auth.disable() */);
        http.csrf(AbstractHttpConfigurer::disable/* = (auth) -> auth.disable() */);
        http.cors((cors) -> cors.configurationSource(reactConfigurationSource));

        http.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/")).permitAll());
        http.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/mypage")).authenticated());
        http.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/admin/**")).hasRole("ADMIN"));
        http.authorizeHttpRequests(request -> request.requestMatchers(new AntPathRequestMatcher("/api/**")).authenticated());
//      http.authorizeHttpRequests((authorize) -> authorize.anyRequest().authenticated());
//      http.formLogin(Customizer.withDefaults())
        http.formLogin(form -> form.loginPage("/login").permitAll());
        http.httpBasic(Customizer.withDefaults());

        http.logout(logout -> logout
                .logoutUrl("/logout")
                .logoutSuccessUrl("/login")
                .logoutSuccessHandler(logoutSuccessHandler)
                .clearAuthentication(true)
//              .invalidateHttpSession(true)
//              .addLogoutHandler((request, response, authentication) -> {
//                  HttpSession session = request.getSession();
//                  session.invalidate();
//              })
        );

//      http.exceptionHandling(except -> except
//              .authenticationEntryPoint(authenticationEntryPoint)
//              .accessDeniedHandler(accessDeniedHandler)
//      );
        return http.build();
    }

    @Bean
    public AuthenticationManager authManager(HttpSecurity http) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        authenticationManagerBuilder.authenticationProvider(authenticationProvider);
        return authenticationManagerBuilder.build();
    }

//  @Bean
//  public InMemoryUserDetailsManager userDetailsService(PasswordEncoder passwordEncoder) {
//      UserDetails aaron = User.withUsername("aaron").password(passwordEncoder.encode("456")).roles("USER", "ADMIN").build();
//      UserDetails baron = User.withUsername("baron").password(passwordEncoder.encode("456")).roles("USER").build();
//      UserDetails caron = User.withUsername("caron").password(passwordEncoder.encode("456")).roles("USER").build();
//      return new InMemoryUserDetailsManager(aaron, baron, caron);
//  }
}
