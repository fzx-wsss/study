package com.wsss.security.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;

@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Autowired
	private MyClientDetailsService myClientDetailsService;
	@Autowired
	private MyTokenStore myTokenStore;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
		clients.withClientDetails(myClientDetailsService);
		// clients.inMemory().withClient("client").secret(passwordEncoder.encode("123")).scopes("read").autoApprove(true)
		// .authorities("WRIGTH_READ")
		// .authorizedGrantTypes("refresh_token", "authorization_code",
		// "password", "client_credentials");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		// myDefaultTokenServices.setTokenEnhancer(new MyTokenEnhancer());
		// myDefaultTokenServices.setTokenStore(myTokenStore);
		endpoints.tokenStore(myTokenStore)// .tokenServices(myDefaultTokenServices)
				.userDetailsService(new MyUserDetailsService()).authenticationManager(authenticationManager)
				.allowedTokenEndpointRequestMethods(HttpMethod.POST, HttpMethod.GET)
				.exceptionTranslator(loggingExceptionTranslator());
		;
	}

	/** 允许表单验证，浏览器直接发送post请求即可获取tocken */
	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()");
		oauthServer.allowFormAuthenticationForClients();
	}

	@Bean
	public WebResponseExceptionTranslator loggingExceptionTranslator() {
		return new DefaultWebResponseExceptionTranslator() {
			@Override
			public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
				// This is the line that prints the stack trace to the log. You
				// can customise this to format the trace etc if you like
				e.printStackTrace();

				// Carry on handling the exception
				ResponseEntity<OAuth2Exception> responseEntity = super.translate(e);
				HttpHeaders headers = new HttpHeaders();
				headers.setAll(responseEntity.getHeaders().toSingleValueMap());
				OAuth2Exception excBody = responseEntity.getBody();
				return new ResponseEntity<>(excBody, headers, responseEntity.getStatusCode());
			}
		};
	}

}