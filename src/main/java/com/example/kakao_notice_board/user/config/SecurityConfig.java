package com.example.kakao_notice_board.user.config;

import com.example.kakao_notice_board.user.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
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

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity, AuthenticationManager authenticationManager) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> cors.configurationSource(request -> {
                    CorsConfiguration config = new CorsConfiguration();
                    config.addAllowedOriginPattern("*");
                    config.addAllowedMethod("*");
                    config.addAllowedHeader("*");
                    config.setAllowCredentials(true);
                    return config;
                }))
                .authorizeHttpRequests(auth -> {
                    auth.requestMatchers("/user/login", "/user/register").permitAll();
                    auth.requestMatchers("/boards/write", "/boards/*/edit", "/boards/*/delete", "/likes/*", "/user/boards/*", "/user/edit", "/user/delete").authenticated();
                    auth.requestMatchers("/user/admin/**").hasAuthority("ADMIN");
                    auth.anyRequest().permitAll();
                })
                .addFilterBefore(new JsonUsernamePasswordAuthenticationFilter(authenticationManager), UsernamePasswordAuthenticationFilter.class)
                .formLogin(formLogin -> {
                    formLogin.loginPage("/user/login")
                            .usernameParameter("username")
                            .passwordParameter("password")
                            .failureHandler(new LoginFailureHandler())
                            .successHandler(new LoginSuccessHandler());
                })
                .logout(logout -> {
                    logout.logoutUrl("/user/logout")
                            .invalidateHttpSession(true)
                            .deleteCookies("JSESSIONID");
                });

        return httpSecurity.build();
    }


    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}