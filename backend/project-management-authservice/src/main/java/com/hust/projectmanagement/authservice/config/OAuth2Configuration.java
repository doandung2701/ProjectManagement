package com.hust.projectmanagement.authservice.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;
import org.springframework.security.oauth2.provider.token.store.KeyStoreKeyFactory;

import com.hust.projectmanagement.authservice.security.services.UserDetailsServiceImpl;

@Configuration
@EnableAuthorizationServer
public class OAuth2Configuration extends AuthorizationServerConfigurerAdapter {
	@Autowired
	UserDetailsServiceImpl userDetailsService;
	@Autowired
	private Environment env;

	@Autowired
	@Qualifier("authenticationManagerBean")
	private AuthenticationManager authenticationManager;

	@Override
	public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

		clients.inMemory().withClient("browser").authorizedGrantTypes("refresh_token", "password").scopes("ui").and()
				.withClient("account-service").secret(env.getProperty("ACCOUNT_SERVICE_PASSWORD"))
				.authorizedGrantTypes("client_credentials", "refresh_token").scopes("server").and()
				.withClient("statistics-service").secret(env.getProperty("STATISTICS_SERVICE_PASSWORD"))
				.authorizedGrantTypes("client_credentials", "refresh_token").scopes("server").and()
				.withClient("notification-service").secret(env.getProperty("NOTIFICATION_SERVICE_PASSWORD"))
				.authorizedGrantTypes("client_credentials", "refresh_token").scopes("server");
	}

	@Override
	public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
		endpoints.tokenStore(tokenStore()).tokenEnhancer(jwtTokenEnhancer())
				.authenticationManager(authenticationManager).userDetailsService(userDetailsService);
	}

	@Override
	public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
		oauthServer.tokenKeyAccess("permitAll()").checkTokenAccess("isAuthenticated()")
				.passwordEncoder(NoOpPasswordEncoder.getInstance());
	}

	@Bean
	public TokenStore tokenStore() {

		return new JwtTokenStore(jwtTokenEnhancer());
	}

	@Bean
	protected JwtAccessTokenConverter jwtTokenEnhancer() {

		KeyStoreKeyFactory keyStoreKeyFactory = new KeyStoreKeyFactory(new ClassPathResource("jwt.jks"),
				"mySecretKey".toCharArray());
		JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
		converter.setKeyPair(keyStoreKeyFactory.getKeyPair("jwt"));
		return converter;
	}
}
