package com.damdev.oauth.client;

import java.util.Optional;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Author : zenic88
 * Created : 02/11/2018
 */
public interface ClientRepository extends MongoRepository<Client, String> {

  Optional<Client> findByClientName(String clientName);
}
