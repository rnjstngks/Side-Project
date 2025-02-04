package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.csrf.CookieCsrfTokenRepository;
import org.springframework.security.web.header.writers.frameoptions.XFrameOptionsHeaderWriter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


@Configuration //이 파일이 스프링의 환결 설정 파일임을 의미하는 애너테이션 
@EnableWebSecurity // 모든 요청 URL이 스프링 시큐리티의 제어를 받도록 만드는 애너테이션
// @EnableWebSecurity 애너테이션을 사용하면 스프링 시큐리티를 활성화하는 역할을 합니다.
//@EnableMethodSecurity(prePostEnabled = true) // @PreAuthorize 애너테이션을 사용하기 위한 애너테이션이다.
public class SecurityConfig {
	// Bean은 스프링에 의해 생성 또는 관리되는 객체를 의미
	@Bean
	SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
		// 아래 코드는 인증되지 않은 모든 페이지의 요청을 허락한다는 의미, 따라서 로그인하지 않더라도 모든 페이지에 접근할 수 있도록 함
		// H2 콘솔은 스프링 시큐리티 CSRF 처리를 예외로 함
		http
			.authorizeHttpRequests((authorizeHttpRequests) -> authorizeHttpRequests
			.requestMatchers(new AntPathRequestMatcher("/**")).permitAll())
			.csrf((csrf) -> csrf.ignoringRequestMatchers(new AntPathRequestMatcher("/h2-console/**"))
//					.csrfTokenRepository(CookieCsrfTokenRepository.withHttpOnlyFalse()) // ✅ CSRF 토큰을 쿠키로 제공
					)
			.headers((headers) -> headers
					.addHeaderWriter(new XFrameOptionsHeaderWriter(
							XFrameOptionsHeaderWriter.XFrameOptionsMode.SAMEORIGIN)))
			.formLogin((formLogin) -> formLogin	// Login이 정상적으로 되면, / 페이지로 돌아감
					.loginPage("/user/login")
					.defaultSuccessUrl("/"))
			.logout((logout) -> logout	// logout이 정상적으로 되면, / 페이지로 돌아감
					.logoutRequestMatcher(new AntPathRequestMatcher("/user/logout"))
					.logoutSuccessUrl("/")
					.invalidateHttpSession(true))	// 해당 설정을 통해 logout 시 생성된 사용자 세션도 삭제하도록 처리
			;
		return http.build();
	}
	
	@Bean
	PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	// AuthenticationManager는 스프링 시큐리티 인증을 처리합니다.
	AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}
}
