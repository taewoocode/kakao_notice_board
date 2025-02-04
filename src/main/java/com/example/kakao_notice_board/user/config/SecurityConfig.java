package com.example.kakao_notice_board.user.config;

import com.example.kakao_notice_board.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.web.cors.CorsConfiguration;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder encoder() {
        return new BCryptPasswordEncoder();
    }

    private final UserRepository userRepository;

    // 로그인하지 않은 유저들만 접근 가능한 URL
    private static final String[] anonymousUserUrl = {"/users/login", "/users/join"};

    // 로그인한 유저들만 접근 가능한 URL
    private static final String[] authenticatedUserUrl = {"/boards/write", "/boards/**/**/edit", "/boards/**/**/delete", "/likes/**", "/users/boards/**", "/users/edit", "/users/delete"};

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> new CorsConfiguration().applyPermitDefaultValues()))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers(anonymousUserUrl).permitAll();
                    auth.requestMatchers(authenticatedUserUrl).authenticated();
                    auth.requestMatchers("/users/admin/**").hasAuthority("ADMIN");
                    auth.anyRequest().permitAll();
                })
                .formLogin(formLogin -> {
                    formLogin.loginPage("/users/login")  // 로그인 페이지
                            .usernameParameter("username")  // 로그인에 사용될 id
                            .passwordParameter("password")  // 로그인에 사용될 password
                            .failureUrl("/users/login?fail")  // 로그인 실패 시 redirect될 URL
                            .successHandler(new LoginSuccessHandler(userRepository));
                })
                .logout(logout -> {
                    logout.logoutUrl("/users/logout")  // 로그아웃 URL
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID");
                });

        return httpSecurity.build();
    }
}
