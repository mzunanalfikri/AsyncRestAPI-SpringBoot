package com.gdplabs.AsyncRestAPI.controller;

import com.gdplabs.AsyncRestAPI.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.concurrent.CompletableFuture;

@RestController
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService service;

    @GetMapping(value = "/users")
    public CompletableFuture<ResponseEntity> findAllUsers(){
        return service.findAllUser().thenApply(ResponseEntity::ok); //async method
    }

    @PostMapping(value="/users", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE}, produces = "application/json")
    public ResponseEntity saveUsersCSV(@RequestParam(value = "files")MultipartFile[] files) throws Exception {
        logger.info("post a file ...");
        int i = 1;
        for (MultipartFile file : files){
            logger.info("start parsing file {}", i);
            service.saveUserFromCSV(file); // async completablefuture method
            logger.info("done saving");
            i++;
        }
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
}
