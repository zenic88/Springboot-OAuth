package com.damdev.oauth.config;

import com.damdev.oauth.user.CustomUserDetails;
import com.damdev.oauth.user.User;
import com.damdev.oauth.user.UserRepository;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import javax.annotation.Resource;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * Author : zenic88
 * Created : 02/11/2018
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

  @Resource
  UserRepository userRepo;

  @Override
  public UserDetails loadUserByUsername(String userId) throws UsernameNotFoundException {
    Optional<User> byUserId = userRepo.findByUserId(userId);
    User user = byUserId.orElseThrow(() -> new UsernameNotFoundException(userId));

    return new CustomUserDetails(user, authorities(user));
  }

  public Set<GrantedAuthority> authorities(User user) {
    Set<GrantedAuthority> authorities = new HashSet<>();

    authorities.add(new SimpleGrantedAuthority(user.getRole()));

    return authorities;
  }
}
