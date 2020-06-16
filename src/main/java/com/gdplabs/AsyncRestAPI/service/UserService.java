package com.gdplabs.AsyncRestAPI.service;

import com.gdplabs.AsyncRestAPI.models.User;
import com.gdplabs.AsyncRestAPI.repositories.UserRepository;
import org.bson.types.ObjectId;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
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
            Thread.sleep(5000); // sleep for calculations
        } catch (InterruptedException e){

        }
        return CompletableFuture.completedFuture(users);
    }

    //save users to database from file csv
    @Async
    public CompletableFuture<List<User>> saveUserFromCSV(MultipartFile file) throws Exception{
        long start = System.currentTimeMillis();
        List<User> users = parseCSVFile(file);
        logger.info("saving list of users of size {}" , users.size(), ". Thread work : " + Thread.currentThread().getName());
        repository.saveAll(users);
        long end = System.currentTimeMillis();
        logger.info("Total time to save : {}", (start-end));
        return CompletableFuture.completedFuture(users);
    }

    private List<User> parseCSVFile(final MultipartFile file) throws Exception {
        final List<User> users = new ArrayList<>();
        logger.info("Start parsing CSV file");
        try {

            try (final BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))){
                String line;
                while ((line = br.readLine()) != null ){
                    final String[] data = line.split(",");
                    final User user = new User();
                    user.setId(ObjectId.get().toString());
                    user.setName(data[0]);
                    user.setEmail(data[1]);
                    user.setGender(data[2]);
                    user.setCompany(data[3]);
                    users.add(user);
                }
                System.out.println("parse csv done");
                return users;
            }
        } catch (final IOException e){
            logger.error("Failed to parse csv file {}", e);
            throw new Exception("Failed to parse CSV file {}", e);
        }
    }
}
