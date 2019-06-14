package com.wsss.security.oauth2;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.OAuth2Request;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.DefaultTokenServices;

/**
 * 处理来自于外部APP请求的token生成和刷新处理
 * 
 * @author qichenchen
 * @date 19-2-18
 */
// @Component
public class MyDefaultTokenServices extends DefaultTokenServices {

	@Override
	public void afterPropertiesSet() throws Exception {
		// TODO Auto-generated method stub
		super.afterPropertiesSet();
	}

	@Override
	public OAuth2AccessToken createAccessToken(OAuth2Authentication authentication) throws AuthenticationException {
		System.out.println("createAccessToken");
		return super.createAccessToken(authentication);
	}

	@Override
	public OAuth2AccessToken refreshAccessToken(String refreshTokenValue, TokenRequest tokenRequest)
			throws AuthenticationException {
		System.out.println("refreshAccessToken");
		return super.refreshAccessToken(refreshTokenValue, tokenRequest);
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		System.out.println("refreshAccessToken");
		return super.getAccessToken(authentication);
	}

	@Override
	protected boolean isExpired(OAuth2RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		return super.isExpired(refreshToken);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String accessToken) {
		// TODO Auto-generated method stub
		return super.readAccessToken(accessToken);
	}

	@Override
	public OAuth2Authentication loadAuthentication(String accessTokenValue)
			throws AuthenticationException, InvalidTokenException {
		// TODO Auto-generated method stub
		return super.loadAuthentication(accessTokenValue);
	}

	@Override
	public String getClientId(String tokenValue) {
		// TODO Auto-generated method stub
		return super.getClientId(tokenValue);
	}

	@Override
	public boolean revokeToken(String tokenValue) {
		// TODO Auto-generated method stub
		return super.revokeToken(tokenValue);
	}

	@Override
	protected int getAccessTokenValiditySeconds(OAuth2Request clientAuth) {
		// TODO Auto-generated method stub
		return super.getAccessTokenValiditySeconds(clientAuth);
	}

	@Override
	protected int getRefreshTokenValiditySeconds(OAuth2Request clientAuth) {
		// TODO Auto-generated method stub
		return super.getRefreshTokenValiditySeconds(clientAuth);
	}

	@Override
	protected boolean isSupportRefreshToken(OAuth2Request clientAuth) {
		// TODO Auto-generated method stub
		return super.isSupportRefreshToken(clientAuth);
	}

}