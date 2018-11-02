package com.damdev.oauth.user;

import java.util.Date;
import lombok.Data;
import lombok.Generated;
import org.springframework.data.annotation.Id;

/**
 * Author : zenic88
 * Created : 02/11/2018
 */
@Data
public class User {

  @Id
  @Generated
  private String id;

  private String userId;

  private String userName;

  private String password;

  private String status;

  private String role;

  private Date regDate;

  private Date modifyDate;
}
