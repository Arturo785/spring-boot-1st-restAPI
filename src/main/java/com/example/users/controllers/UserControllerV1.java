package com.example.users.controllers;


import com.example.users.models.User;
import com.example.users.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/users")
public class UserControllerV1 {

    private final UserService userService;

    // better use constructor injection when the class CAN'T WORK WITH THAT DEPENDENCY
    // If is not mandatory or could change use Getter injection
    // Try not to use field injection
    @Autowired // with constructor injection
    public UserControllerV1(UserService userService) {
        this.userService = userService;
    }

    @GetMapping // GET
    public ResponseEntity<List<User>>
    getUsers(@RequestParam(value = "startsWith", required = false) String startsWith){
        return new ResponseEntity<>(userService.getUsers(startsWith), HttpStatus.OK);
    }

    @GetMapping(value = "/{username}") // path param
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username){
        return new ResponseEntity<>(userService.getUser(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> createUser(@RequestBody User user){
        return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
    }

    @PutMapping(value = "/{username}")
    public ResponseEntity<User> updateUser(@PathVariable("username") String username, @RequestBody User user){
        return new ResponseEntity<>(userService.updateUser(user, username), HttpStatus.OK);
    }

    @DeleteMapping(value = "/{username}")
    public ResponseEntity<Void> deleteUser(@PathVariable("username") String username){
        userService.deleteUser(username);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
