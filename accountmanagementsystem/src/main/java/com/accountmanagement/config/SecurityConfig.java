package com.accountmanagement.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final AuthFilter authFilter;

    SecurityConfig(AuthFilter authFilter) {
        this.authFilter = authFilter;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {
                })
                .authorizeHttpRequests(
                        request -> request
                                .requestMatchers(
                                        "/auth/v1/organization/register",
                                        "/auth/v1/user/register",
                                        "/auth/v1/user/verify/email/otp",
                                        "/auth/v1/user/resend/verification/otp/**",
                                        "/auth/v1/subscription/organization/add",
                                        "/auth/v1/subscription/payment/add",
                                        "/auth/v1/subscription/payment/webhook",
                                        "/auth/v1/user/login",
                                        "/auth/v1/user/verify/otp",
                                        "/auth/v1/user/change/temporary/password",
                                        "/v1/organization/update/**",
                                        "/v1/organization/delete/**",
                                        "/v1/organization",
                                        "/v1/user/update/**",
                                        "/v1/user/delete/**",
                                        "/v1/subscription/plan",
                                        "/v1/subscription/feature/add",
                                        "/v1/subscription/feature/update/**",
                                        "/v1/subscription/feature/delete/**",
                                        "/v1/subscription/feature",
                                        "/v1/subscription/plan/feature/add",
                                        "/v1/subscription/plan/feature/update/**",
                                        "/v1/subscription/plan/feature/delete/**",
                                        "/v1/subscription/plan/feature",
                                        "/v1/subscription/organization/add",
                                        "/v1/subscription/organization/update/**",
                                        "/v1/subscription/organization/delete/**",
                                        "/v1/subscription/organization",
                                        "/v1/subscription/payment",
                                        "/v1/user/refreshKey/{refreshKey}",
                                        "/v1/user/verify/reset/otp",
                                        "/v1/user/forgot/password/**",
                                        "/v1/user/change/password",
                                        "/v1/user/verify/reset/otp",
                                        "/swagger-ui/**",
                                        "/v3/api-docs/**",
                                        "/swagger-ui.html")
                                .permitAll()
                                .anyRequest().authenticated())
                .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class);
        return httpSecurity.build();
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
