package com.vendingMachine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter{

	// PasswordEncoder 를 bean 으로 IoC 에 등록
	@Bean
	public BCryptPasswordEncoder encoder() {
		return new BCryptPasswordEncoder();
	}


	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();   // CSRF 비활성화
		http.authorizeRequests()
			// 로그인/회원가입 페이지는 모두 접근 가능
			.antMatchers("/", "/login", "/signUp", "/accFind", "/registUser", "/idCheck", "/smsCertif").permitAll()
			// API 경로는 인증 필요
			.antMatchers("/api/**").authenticated()
			// 사용자 페이지는 인증 필요
			.antMatchers("/user/**").authenticated()
			.antMatchers("/product/**", "/stock/**", "/vending/**", "/sales/**").authenticated()
			// 관리자 페이지는 ADMIN 권한 필요
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			// 기존 경로 호환성
			.antMatchers("/landing/user/**").authenticated()
			.antMatchers("/landing/admin/**").access("hasRole('ROLE_ADMIN')")
			// 문의하기는 인증 필요
			.antMatchers("/inquiry/**").authenticated()
			// 나머지는 모두 접근 가능
			.anyRequest().permitAll()
			.and()
			.formLogin()
			.loginPage("/login")
			.usernameParameter("userid")
			.passwordParameter("userpassword")
			.loginProcessingUrl("/loginOk")
			.defaultSuccessUrl("/main")  // 로그인 후 메인 페이지로
			.and()
			.rememberMe()
			.key("secret")
			.rememberMeParameter("autoLogin")
			.tokenValiditySeconds(86400)
			.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/login")  // 로그아웃 후 로그인 페이지로
			.invalidateHttpSession(true)
			.deleteCookies("JSESSIONID");
	}

}
