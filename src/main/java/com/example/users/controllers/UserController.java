package com.example.users.controllers;


import com.example.users.entities.User;
import com.example.users.services.NewUserService;
import com.example.users.services.UserService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.Cacheable;

import javax.websocket.server.PathParam;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {


    private final NewUserService userService;

    @Autowired
    public UserController(NewUserService userService) {
        this.userService = userService;
    }

    //http://localhost:8080/users?page=4
    @GetMapping()
    public ResponseEntity<Page<User>> getUsers(@RequestParam Integer page){
        return new ResponseEntity<>(userService.getUsers(page), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    @ApiOperation(value = "returns an user for given id", response = User.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "OK"),
            @ApiResponse(code = 404, message = "Not found"),
    })
    public ResponseEntity<User> getUserByiId(@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(userService.getUserById(userId), HttpStatus.OK);
    }


    @GetMapping("username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable("username") String username){
        return new ResponseEntity<>(userService.getUserByUsername(username), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<User> authenticate(@RequestBody User user){
        return new ResponseEntity<>(userService.authenticate(user.getUsername(), user.getPassword()), HttpStatus.OK);
    }

    @GetMapping("byParam/{param}")
    public ResponseEntity<List<User>> getUsersByParam(@PathVariable String param){
        return new ResponseEntity<>(userService.getUsersWithParam(param), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Integer id){
        userService.deleteUserById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
