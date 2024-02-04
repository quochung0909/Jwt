package com.nqh.jwt.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import com.nqh.jwt.filter.JwtFilter;
import com.nqh.jwt.service.UserInfoService;

import jakarta.servlet.http.HttpServletRequest;

/*
 * SecurityConfig là class cấu hình bảo mật
 * 
 * Class này sẽ được sử dụng trong JwtApplication
 * 
 * Class này sẽ cấu hình các thông tin bảo mật như:
 * - Cấu hình CORS
 * - Cấu hình CSRF
 * - Cấu hình các request được phép truy cập
 * - Cấu hình các request cần xác thực
 * - Cấu hình các thông tin về xác thực
 * - Cấu hình các thông tin về quyền truy cập
 * - Cấu hình các thông tin về session
 * - Cấu hình các thông tin về filter
 */
@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public UserDetailsService userDetailsService() {
        return new UserInfoService();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity.csrf(csrf -> csrf.disable()) 
                .cors(cors -> cors.configurationSource( 
                        new CorsConfigurationSource() {

                            @Override
                            public CorsConfiguration getCorsConfiguration(@SuppressWarnings("null") HttpServletRequest request) {
                                CorsConfiguration config = new CorsConfiguration();
                                config.setAllowedOrigins(Arrays.asList("http://localhost:8080"));
                                config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE"));
                                config.setAllowCredentials(true);
                                return config;
                            }
                        }))
                .authorizeHttpRequests(auth -> auth 
                        .requestMatchers("/auth/addUser", "/auth/login", "/login" ) 
                        .permitAll()
                        .anyRequest().authenticated())
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
        authenticationProvider.setUserDetailsService(userDetailsService());
        authenticationProvider.setPasswordEncoder(passwordEncoder());
        return authenticationProvider;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

}
