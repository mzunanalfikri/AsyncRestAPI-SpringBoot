package com.gdplabs.AsyncRestAPI.service;

import com.gdplabs.AsyncRestAPI.models.User;
import com.gdplabs.AsyncRestAPI.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Service
public class UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository repository;

    @Async
    public CompletableFuture<List<User>> findAllUser() {
        logger.info(" get all user by thread : " + Thread.currentThread().getName());
        List<User> users = repository.findAll();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e){

        }
        return CompletableFuture.completedFuture(users);
    }
}
