package com.example.users.services;


import com.example.users.models.User;
import com.github.javafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

// the service implements the business logic
@Service
public class UserService {

  //  @Autowired // not best practice
    private Faker faker;

    private final List<User> users = new ArrayList<>();

    @Autowired // setter injection instead of field injection
    public void setFaker(Faker faker){
        this.faker = faker;
    }

    @PostConstruct
    public void init(){
        for(int i = 0; i < 100; i++){
            User dummy = new User(faker.name().username(),faker.funnyName().name(), faker.dragonBall().character());
            users.add(dummy);
        }
    }

    public List<User> getUsers(String startsWith) {
        if(startsWith != null){
            return users.stream().filter(user -> user.getUsername().startsWith(startsWith))
                    .collect(Collectors.toList());
        }
        // no param provided
        return users;


    }

    public User getUser(String username){
        return users.stream().filter(user -> user.getUsername().equals(username)).findAny()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("User %s not found", username)));
    }

    public User createUser(User user){
        if(users.stream().anyMatch(userLambda -> userLambda.getUsername().equals(user.getUsername()))){
            throw new ResponseStatusException(HttpStatus.CONFLICT, String.format("User: %s already exists", user.getUsername()));
        }

        users.add(user);
        return user;
    }

    public User updateUser(User user, String username){
        User userToUpdate = getUser(username);

        userToUpdate.setNickname(user.getNickname());
        userToUpdate.setPassword(user.getPassword());

        return userToUpdate;
    }

    public void deleteUser(String username) {
        User userToDelete = getUser(username);

        users.remove(userToDelete);
    }
}
