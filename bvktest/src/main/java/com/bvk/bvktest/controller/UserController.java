package com.bvk.bvktest.controller;

import com.bvk.bvktest.exception.ResourceNotFoundException;
import com.bvk.bvktest.model.User;
import com.bvk.bvktest.payload.request.UpdateProfileRequest;
import com.bvk.bvktest.repository.UserRepository;
import com.bvk.bvktest.security.CurrentUser;
import com.bvk.bvktest.security.UserPrincipal;
import com.bvk.bvktest.services.ProfileService;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProfileService profileService;

    @GetMapping("/user/me")
    @PreAuthorize("hasRole('USER')")
    public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
        return userRepository.findById(userPrincipal.getId())
                .orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
    }

    @PostMapping("/user/update-profile")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<?> updateProfileRequest(@CurrentUser UserPrincipal userPrincipal,
        @ModelAttribute UpdateProfileRequest request) throws UsernameNotFoundException, IOException {
        
        return ResponseEntity.ok(profileService.updateProfile(request));
    }
}

