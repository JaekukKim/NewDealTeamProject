package com.studycafe.utils.config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import com.studycafe.member.auth.PrincipalDetailsService;
import com.studycafe.member.oauth2.service.Oauth2MemberAdaptor;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
	
	private final Oauth2MemberAdaptor customOauth2UserService;
	private final PrincipalDetailsService principalDetailsService;
	private final DataSource dataSource;
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

		http.csrf().disable();
		http.authorizeRequests()
			.antMatchers("/member/**").authenticated()
			.antMatchers("/manager/**").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER')")
//			.antMatchers("/studyregistration").access("hasRole('ROLE_ADMIN') or hasRole('ROLE_MANAGER') or hasRole('TEAM_HEAD')")
			.antMatchers("/admin/**").access("hasRole('ROLE_ADMIN')")
			.anyRequest().permitAll()
		.and()
			.formLogin()
			.loginPage("/loginform")
			.defaultSuccessUrl("/")
		.and()
			.logout()
			.logoutUrl("/logout")
			.logoutSuccessUrl("/")
		.and()
			.rememberMe()
			.userDetailsService(principalDetailsService)
			.tokenRepository(tokenRepository())
		.and()
			.oauth2Login()
			.userInfoEndpoint() // oauth2 로그인 성공시 가져올 설정.
			.userService(customOauth2UserService);
			
		return http.build();
	}

	@Bean
	public static BCryptPasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	 @Bean
	 public PersistentTokenRepository tokenRepository() { 
	        JdbcTokenRepositoryImpl jdbcTokenRepository = new JdbcTokenRepositoryImpl();
	        jdbcTokenRepository.setDataSource(dataSource);
	        return jdbcTokenRepository;
	    }
	
}
