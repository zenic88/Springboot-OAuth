package com.damdev.oauth.client;

import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;

/**
 * Author : zenic88
 * Created : 02/11/2018
 */
@Data
public class Client {

  @Id
  @Generated
  private String id;

  private String clientName;

  private String clientId;

  private String clientSecret;

  private int accessTokenValidSeconds;

  private int refreshTokenValidSeconds;

  private String jwtTokenKey;
}
