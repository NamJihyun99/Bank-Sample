package shop.mtcoding.bank.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import shop.mtcoding.bank.domain.user.UserEnum;

@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("debug: BCryptPasswordEncoder bean registered");
        return new BCryptPasswordEncoder();
    }

// JWT 필터 등록 필요

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.headers().frameOptions().disable();    // iframe 허용 안함
        http.csrf().disable();  // postman 작동 위해
        http.cors().configurationSource(configurationSource());

        // jSeesionId를 서버에서 관리하지 않음
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable(); // 브라우저 팝업창 사용한 사용자 인증 사용하지 않음
        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated()
                .antMatchers("/api/admin/**").hasRole(""+UserEnum.ADMIN)   // ROLE_ADMIN
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedHeader("*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedOriginPattern("*");
        configuration.setAllowCredentials(true);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);

        return source;
    }
}