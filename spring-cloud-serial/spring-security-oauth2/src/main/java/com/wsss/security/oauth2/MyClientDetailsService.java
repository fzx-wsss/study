package com.wsss.security.oauth2;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.provider.ClientDetails;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.ClientRegistrationException;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Component;

@Component
public class MyClientDetailsService implements ClientDetailsService {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public ClientDetails loadClientByClientId(String clientId) throws ClientRegistrationException {
		BaseClientDetails details = new BaseClientDetails(clientId, "oauth2-resource", "read",
				"refresh_token authorization_code password client_credentials", "123");
		details.setClientSecret(passwordEncoder.encode("123"));
		details.setAuthorizedGrantTypes(
				Arrays.asList("refresh_token", "authorization_code", "password", "client_credentials"));
		return details;
	}

}
