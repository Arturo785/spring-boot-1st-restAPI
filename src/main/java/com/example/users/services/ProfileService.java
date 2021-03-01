package com.example.users.services;


import com.example.users.entities.Profile;
import com.example.users.entities.User;
import com.example.users.repositories.ProfileRepository;
import com.example.users.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileService {

    private final ProfileRepository profileRepository;

    private final UserRepository userRepository;

    @Autowired
    public ProfileService(ProfileRepository profileRepository, UserRepository userRepository) {
        this.profileRepository = profileRepository;
        this.userRepository = userRepository;
    }

    public Profile createProfile(Profile profile, Integer userId){
        Optional<User> userRetrieved = userRepository.findById(userId);

        if(userRetrieved.isPresent()){
            profile.setUser(userRetrieved.get());
            return profileRepository.save(profile);
        }
        else{
            throw new ResponseStatusException(HttpStatus.NOT_FOUND,
                    String.format("There is no user with %d id", userId));
        }
    }


    public Profile getProfilesById(Integer profileId, Integer userId) {
        return profileRepository.findByUserIdAndProfileId(userId, profileId).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        String.format("There is no user with %d id", userId)));

    }
}
