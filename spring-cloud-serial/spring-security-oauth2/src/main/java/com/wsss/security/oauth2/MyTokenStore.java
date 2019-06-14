package com.wsss.security.oauth2;

import java.util.Collection;

import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2RefreshToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.AuthenticationKeyGenerator;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
import org.springframework.stereotype.Component;

/**
 * @author qichenchen
 * @date 18-12-14
 */
@Component
public class MyTokenStore extends InMemoryTokenStore {

	@Override
	public void setFlushInterval(int flushInterval) {
		// TODO Auto-generated method stub
		super.setFlushInterval(flushInterval);
	}

	@Override
	public int getFlushInterval() {
		// TODO Auto-generated method stub
		return super.getFlushInterval();
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		super.clear();
	}

	@Override
	public void setAuthenticationKeyGenerator(AuthenticationKeyGenerator authenticationKeyGenerator) {
		// TODO Auto-generated method stub
		super.setAuthenticationKeyGenerator(authenticationKeyGenerator);
	}

	@Override
	public int getAccessTokenCount() {
		// TODO Auto-generated method stub
		return super.getAccessTokenCount();
	}

	@Override
	public int getRefreshTokenCount() {
		// TODO Auto-generated method stub
		return super.getRefreshTokenCount();
	}

	@Override
	public int getExpiryTokenCount() {
		// TODO Auto-generated method stub
		return super.getExpiryTokenCount();
	}

	@Override
	public OAuth2AccessToken getAccessToken(OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		return super.getAccessToken(authentication);
	}

	@Override
	public OAuth2Authentication readAuthentication(OAuth2AccessToken token) {
		// TODO Auto-generated method stub
		return super.readAuthentication(token);
	}

	@Override
	public OAuth2Authentication readAuthentication(String token) {
		// TODO Auto-generated method stub
		return super.readAuthentication(token);
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(OAuth2RefreshToken token) {
		// TODO Auto-generated method stub
		return super.readAuthenticationForRefreshToken(token);
	}

	@Override
	public OAuth2Authentication readAuthenticationForRefreshToken(String token) {
		// TODO Auto-generated method stub
		return super.readAuthenticationForRefreshToken(token);
	}

	@Override
	public void storeAccessToken(OAuth2AccessToken token, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		super.storeAccessToken(token, authentication);
	}

	@Override
	public void removeAccessToken(OAuth2AccessToken accessToken) {
		// TODO Auto-generated method stub
		super.removeAccessToken(accessToken);
	}

	@Override
	public OAuth2AccessToken readAccessToken(String tokenValue) {
		// TODO Auto-generated method stub
		return super.readAccessToken(tokenValue);
	}

	@Override
	public void removeAccessToken(String tokenValue) {
		// TODO Auto-generated method stub
		super.removeAccessToken(tokenValue);
	}

	@Override
	public void storeRefreshToken(OAuth2RefreshToken refreshToken, OAuth2Authentication authentication) {
		// TODO Auto-generated method stub
		super.storeRefreshToken(refreshToken, authentication);
	}

	@Override
	public OAuth2RefreshToken readRefreshToken(String tokenValue) {
		// TODO Auto-generated method stub
		return super.readRefreshToken(tokenValue);
	}

	@Override
	public void removeRefreshToken(OAuth2RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		super.removeRefreshToken(refreshToken);
	}

	@Override
	public void removeRefreshToken(String tokenValue) {
		// TODO Auto-generated method stub
		super.removeRefreshToken(tokenValue);
	}

	@Override
	public void removeAccessTokenUsingRefreshToken(OAuth2RefreshToken refreshToken) {
		// TODO Auto-generated method stub
		super.removeAccessTokenUsingRefreshToken(refreshToken);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientIdAndUserName(String clientId, String userName) {
		// TODO Auto-generated method stub
		return super.findTokensByClientIdAndUserName(clientId, userName);
	}

	@Override
	public Collection<OAuth2AccessToken> findTokensByClientId(String clientId) {
		// TODO Auto-generated method stub
		return super.findTokensByClientId(clientId);
	}

}