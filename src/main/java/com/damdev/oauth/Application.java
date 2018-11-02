package com.damdev.oauth;

import com.damdev.oauth.client.Client;
import com.damdev.oauth.client.ClientRepository;
import com.damdev.oauth.user.User;
import com.damdev.oauth.user.UserRepository;
import java.util.Optional;
import javax.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@Slf4j
@SpringBootApplication
public class Application implements ApplicationRunner {

  @Resource
  UserRepository userRepo;

  @Resource
  ClientRepository clientRepo;

  @Resource
  PasswordEncoder passwordEncoder;

  public static void main(String[] args) {
    SpringApplication application = new SpringApplication(Application.class);
    application.run(args);
  }

  @Override
  public void run(ApplicationArguments args) {
    Optional<User> byUserId = userRepo.findByUserId("admin");
    User user = byUserId.orElseGet(() -> {
      User addUser = new User();
      addUser.setUserId("admin");
      addUser.setPassword(passwordEncoder.encode("1234"));
      addUser.setUserName("관리자");
      addUser.setRole("ROLE_ADMIN");

      return userRepo.save(addUser);
    });

    Client client = clientRepo.findByClientName("oauth").get();

    log.info(user.toString());
    log.info(client.toString());
  }
}
