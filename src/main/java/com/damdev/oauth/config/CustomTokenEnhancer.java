package com.damdev.oauth.config;

import com.damdev.oauth.user.CustomUserDetails;
import com.damdev.oauth.user.User;
import com.damdev.oauth.user.UserRepository;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;

/**
 * Author : zenic88
 * Created : 02/11/2018
 */
@Slf4j
@Configuration
public class CustomTokenEnhancer implements TokenEnhancer {

  @Resource
  UserRepository userRepo;

  @Override
  public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken,
    OAuth2Authentication oAuth2Authentication) {
    log.info("토큰 생성");

    Map<String, Object> additionalInfo = new HashMap<>();
    // CustomUserDetails 에서 정보를 가지고 온다
    CustomUserDetails userDetails = (CustomUserDetails) oAuth2Authentication.getPrincipal();
    String userId = userDetails.getUsername();
    User user = userRepo.findByUserId(userId).get();

    // 여기서 필요한 정보 추가
    additionalInfo.put("id", user.getId());
    additionalInfo.put("userId", userId);
    additionalInfo.put("status", user.getStatus());
    additionalInfo.put("role", user.getRole());

    ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
    return oAuth2AccessToken;
  }
}
