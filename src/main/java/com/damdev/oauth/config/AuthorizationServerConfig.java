package com.damdev.oauth.config;

import com.damdev.oauth.client.Client;
import com.damdev.oauth.client.ClientRepository;
import java.util.Arrays;
import javax.annotation.Resource;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.security.oauth2.provider.token.TokenEnhancerChain;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtTokenStore;

/**
 * Author : zenic88
 * Created : 02/11/2018
 */
@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableAuthorizationServer
public class AuthorizationServerConfig extends AuthorizationServerConfigurerAdapter {

  private final AuthenticationManager authenticationManager;

  @Autowired
  UserDetailsServiceImpl userDetailServiceImpl;

  @Resource
  PasswordEncoder passwordEncoder;

  @Resource
  ClientRepository clientRepo;

  @Override
  public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
    security.passwordEncoder(passwordEncoder);
  }

  @Override
  public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
    log.info("start AuthorizationServerConfig AuthorizationServerEndpointsConfigurer configure");
    TokenEnhancerChain tokenEnhancerChain = new TokenEnhancerChain();
    tokenEnhancerChain.setTokenEnhancers(
      Arrays.asList(tokenEnhancer(), jwtAccessTokenConverter())
    );
    endpoints.tokenStore(tokenStore())
      .tokenEnhancer(tokenEnhancerChain)//추가한 token정보를 추가
      .userDetailsService(userDetailServiceImpl)
      .accessTokenConverter(jwtAccessTokenConverter())
      .authenticationManager(this.authenticationManager);
  }

  @Override
  public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

    log.info("start AuthorizationServerConfig ClientDetailsServiceConfigurer configure");

    String clientName = "oauth";
    Client client = clientRepo.findByClientName(clientName).get();

    clients.inMemory()
      .withClient(client.getClientId())
      .authorizedGrantTypes("password", "refresh_token")
      .authorities("ROLE_USER")
      .scopes("read", "write")
      .resourceIds(clientName)
      .accessTokenValiditySeconds(client.getAccessTokenValidSeconds())
      .refreshTokenValiditySeconds(client.getRefreshTokenValidSeconds())
      .secret("{noop}" + client.getClientSecret());
  }

  @Bean
  public JwtAccessTokenConverter jwtAccessTokenConverter() {
    log.info("start AuthorizationServerConfig JwtAccessTokenConverter ");

    String clientName = "oauth";
    Client client = clientRepo.findByClientName(clientName).get();

    final JwtAccessTokenConverter accessTokenConverter = new JwtAccessTokenConverter();
    accessTokenConverter.setSigningKey(client.getJwtTokenKey());
    return accessTokenConverter;
  }

  @Bean
  public TokenStore tokenStore() {
    log.info("start AuthorizationServerConfig tokenStore ");
    return new JwtTokenStore(jwtAccessTokenConverter());
  }

  @Bean
  public TokenEnhancer tokenEnhancer() {
    log.info("start AuthorizationServerConfig tokenEnhancer ");
    return new CustomTokenEnhancer();
  }
}
