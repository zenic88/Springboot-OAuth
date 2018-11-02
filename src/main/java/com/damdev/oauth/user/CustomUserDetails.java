package com.damdev.oauth.user;

import java.util.Collection;
import java.util.Set;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * Author : zenic88
 * Created : 02/11/2018
 */
public class CustomUserDetails implements UserDetails {

  private String username;
  private String password;
  private Set<GrantedAuthority> authorities;

  public CustomUserDetails(User user, Set<GrantedAuthority> authorities) {
    this.username = user.getUserId();
    this.password = user.getPassword();
    this.authorities = authorities;
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return this.authorities;
  }

  @Override
  public String getPassword() {
    return this.password;
  }

  @Override
  public String getUsername() {
    return this.username;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }
}
