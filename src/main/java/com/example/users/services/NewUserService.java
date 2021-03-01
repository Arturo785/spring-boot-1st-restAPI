package com.example.users.services;

import com.example.users.entities.User;
import com.example.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import springfox.documentation.annotations.Cacheable;

import java.util.List;

@Service
public class NewUserService {

    private final UserRepository userRepository;
    private final int numOfResults = 10;

    @Autowired
    public NewUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }


    public Page<User> getUsers(int page){
        // with pagination
        return userRepository.findAll(PageRequest.of(page, numOfResults));
     //   return userRepository.findAll();
    }

    public User getUserById(Integer userId) {
        return userRepository.findById(userId).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with id: %d does not exists", userId)
                )
        );
    }

    //just an example, when wanting to cache
    @Cacheable("users") // where to save it (configured in cacheConfig)
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("User with username: %s does not exists", username)
                )
        );
    }

    public User authenticate(String username, String password) {
        return userRepository.findByUsernameAndAndPassword(username,password).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "couldn't authenticate"
                )
        );
    }

    public List<User> getUsersWithParam(String param) {
        return userRepository.findAllByAllUsersThatHaveParamInName(param).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        String.format("Could not found users with %s",param)
                )
        );
    }

    @CacheEvict("users") // removes the element from cache
    public void deleteUserById(Integer id){
        User userRetrieved = getUserById(id);
        userRepository.delete(userRetrieved);
    }

}
