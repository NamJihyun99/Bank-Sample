package shop.mtcoding.bank.config;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import shop.mtcoding.bank.dto.ResponseDto;
import shop.mtcoding.bank.util.CustomResponseUtil;

@Configuration
public class SecurityConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());
    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        log.debug("debug: BCryptPasswordEncoder bean is registered.");
        return new BCryptPasswordEncoder();
    }

// JWT 필터 등록 필요

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        log.debug("debug: FilterChain bean is registered.");
        http.headers().frameOptions().disable();    // iframe 허용 안함
        http.csrf().disable();  // postman 작동 위해
        http.cors().configurationSource(configurationSource());

        // jSessionId를 서버에서 관리하지 않음
        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        http.formLogin().disable();
        http.httpBasic().disable(); // 브라우저 팝업창 사용한 사용자 인증 사용하지 않음

        //Exception 인터셉터
        http.exceptionHandling().authenticationEntryPoint((request, response, authException) -> {
            CustomResponseUtil.unAuthentication(response, "로그인 필요");
        });

        http.authorizeRequests()
                .antMatchers("/api/s/**").authenticated()
                .antMatchers("/api/admin/**").hasRole(""+UserEnum.ADMIN)   // ROLE_ADMIN
                .anyRequest().permitAll();

        return http.build();
    }

    public CorsConfigurationSource configurationSource() {
        log.debug("debug: configurationSource cors settings are registered in FilterChain bean.");
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
