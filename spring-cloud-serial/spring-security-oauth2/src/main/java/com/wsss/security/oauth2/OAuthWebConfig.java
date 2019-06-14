package com.wsss.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class OAuthWebConfig extends WebSecurityConfigurerAdapter {
	@Autowired
	private UserDetailsService myUserDetailManager;

	@Autowired
	private PasswordEncoder myPasswordEncoder;

	// @Bean
	// @Override
	// protected UserDetailsService userDetailsService() {
	// InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
	// manager.createUser(
	// User.withUsername("user").password(passwordEncoder.encode("123456")).authorities("USER").build());
	// return manager;
	// }
	//
	@Override
	@Bean
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();
		http.requestMatchers().anyRequest().and().authorizeRequests().antMatchers("/oauth/**").permitAll(); // 支持表单登录
	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		MyDaoAuhthenticationProvider daoAuthenticationProvider = new MyDaoAuhthenticationProvider();
		daoAuthenticationProvider.setUserDetailsService(myUserDetailManager);
		daoAuthenticationProvider.setHideUserNotFoundExceptions(false);
		daoAuthenticationProvider.setPasswordEncoder(myPasswordEncoder);
		auth.authenticationProvider(daoAuthenticationProvider);
		// super.configure(auth);
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
