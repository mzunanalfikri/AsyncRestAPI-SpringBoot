package com.gdplabs.AsyncRestAPI.controller;

import com.gdplabs.AsyncRestAPI.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {
    @Autowired
    private UserService service;

    @GetMapping(value = "/users")
    public CompletableFuture<ResponseEntity> findAllUsers(){
        return service.findAllUser().thenApply(ResponseEntity::ok); //async method
    }
}
