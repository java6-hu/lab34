package com.poly.lab34.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.savedrequest.DefaultSavedRequest;

import jakarta.servlet.http.HttpSession;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true) // Của Bài 2
public class SecurityConfig {

        @Bean
        public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

                // 1. Tắt CSRF và CORS (Của Bài 2)
                http.csrf(csrf -> csrf.disable()).cors(cors -> cors.disable());

                // 2. KHAI BÁO BẮT BUỘC ĐỂ TRÁNH LỖI (Của Bài 2)
                // Mở cửa cho tất cả các đường dẫn, việc chặn sẽ do @PreAuthorize lo

                http.authorizeHttpRequests(auth -> auth
                                .requestMatchers("/", "/poly/url0",
                                                "/login/**",
                                                "/oauth2/**")
                                .permitAll()
                                .anyRequest().authenticated());

                http.formLogin(form -> form
                                .loginPage("/login/form")
                                .loginProcessingUrl("/login/check")
                                .defaultSuccessUrl("/login/success", true)
                                .failureUrl("/login/failure")
                                .permitAll());

                http.logout(logout -> logout
                                .logoutUrl("/logout")
                                .logoutSuccessUrl("/login/exit"));

                // 3. Cấu hình đăng nhập OAuth2 (Của Bài 1)
                http.oauth2Login(login -> login
                                .permitAll()
                                .successHandler((request, response, authentication) -> {

                                        Object principal = authentication.getPrincipal();
                                        String username = "";

                                        if (principal instanceof DefaultOidcUser oidcUser) {
                                                username = oidcUser.getEmail();
                                        } else {
                                                // Trường hợp OAuth2User (Google thường vẫn lấy được email)
                                                username = ((org.springframework.security.oauth2.core.user.OAuth2User) principal)
                                                                .getAttribute("email");
                                        }

                                        String role = "OAUTH";

                                        UserDetails newUser = User.withUsername(username)
                                                        .password("{noop}")
                                                        .roles(role)
                                                        .build();

                                        Authentication newAuth = new UsernamePasswordAuthenticationToken(
                                                        newUser, null, newUser.getAuthorities());

                                        SecurityContextHolder.getContext().setAuthentication(newAuth);

                                        response.sendRedirect("/login/success");
                                }));

                return http.build();
        }
}