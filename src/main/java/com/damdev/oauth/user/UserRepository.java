package com.damdev.oauth.user;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Author : zenic88
 * Created : 02/11/2018
 */
public interface UserRepository extends MongoRepository<User, String> {

  Optional<User> findByUserId(String userId);
}
