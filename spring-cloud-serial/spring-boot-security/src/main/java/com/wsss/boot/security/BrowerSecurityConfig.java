package com.wsss.boot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class BrowerSecurityConfig extends WebSecurityConfigurerAdapter {

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.formLogin() // 定义当需要用户登录时候，转到的登录页面。
				.and().authorizeRequests() // 定义哪些URL需要被保护、哪些不需要被保护
				.anyRequest() // 任何请求,登录后可以访问
				.authenticated();
	}

	@Autowired
	private UserDetailsService myUserDetailManager;

	@Autowired
	private PasswordEncoder myPasswordEncoder;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		MyDaoAuhthenticationProvider daoAuthenticationProvider = new MyDaoAuhthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(myUserDetailManager);
		daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
		daoAuthenticationProvider.setPasswordEncoder(myPasswordEncoder);
		auth.authenticationProvider(daoAuthenticationProvider);
		// super.configure(auth);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		super.configure(web);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}