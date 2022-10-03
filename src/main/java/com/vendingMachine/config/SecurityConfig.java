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
				.antMatchers("/landing/user/**").authenticated()
				.antMatchers("/landing/admin/**").access("hasRole('ROLE_ADMIN')")
				.anyRequest().permitAll()
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("userid")
				.passwordParameter("userpassword")
				.loginProcessingUrl("/loginOk")
				.defaultSuccessUrl("/")
				.and()
				.rememberMe()
				.key("secret")
				.rememberMeParameter("autoLogin")
				.tokenValiditySeconds(86400)
				
//				.userDetailsService()
				.and()
				.logout()
				.logoutUrl("/logout")
				.invalidateHttpSession(true)
				
				
				
				;
		}

}
