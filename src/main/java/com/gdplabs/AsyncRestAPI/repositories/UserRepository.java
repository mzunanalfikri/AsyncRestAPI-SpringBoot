package com.gdplabs.AsyncRestAPI.repositories;

import com.gdplabs.AsyncRestAPI.models.User;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface UserRepository extends MongoRepository<User, String> {
    List<User> findAll();
}
