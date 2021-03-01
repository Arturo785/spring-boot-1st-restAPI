package com.example.users.controllers;


import com.example.users.entities.Profile;
import com.example.users.services.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("users/{userId}/profiles")
@RestController
public class ProfileController {


    private final ProfileService profileService;

    @Autowired
    public ProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    @PostMapping
    public ResponseEntity<Profile> create(@RequestBody Profile profile,@PathVariable("userId") Integer userId){
        return new ResponseEntity<>(profileService.createProfile(profile, userId), HttpStatus.CREATED);
    }

    @GetMapping("{profileId}")
    public ResponseEntity<Profile>
    getProfilesById(@PathVariable("profileId") Integer profileId, @PathVariable("userId") Integer userId){
        return new ResponseEntity<>(profileService.getProfilesById(profileId, userId), HttpStatus.OK);
    }
}
