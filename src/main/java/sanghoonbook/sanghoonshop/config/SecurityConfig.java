package sanghoonbook.sanghoonshop.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.config.http.SessionCreationPolicy;

@Configuration
public class SecurityConfig {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable())  // 🔹 CSRF 보호 비활성화 (POST 요청 허용)
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))  // 🔹 JWT를 사용할 경우 필요
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/auth/signup", "/auth/login").permitAll()  // 🔹 회원가입 & 로그인은 인증 없이 가능
                        .anyRequest().authenticated()  // 🔹 그 외 요청은 인증 필요
                )
                .formLogin(form -> form.disable())  // 🔹 기본 로그인 폼 비활성화
                .httpBasic(basic -> basic.disable());  // 🔹 기본 HTTP Basic 인증 비활성화

        return http.build();
    }
}