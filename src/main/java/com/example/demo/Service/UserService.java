package com.example.demo.Service;

import org.springframework.stereotype.Service;

import com.example.demo.Exception.ResourceNotFoundException;
import com.example.demo.Model.TDUser;
import com.example.demo.Repository.UserRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    
    public TDUser findById(Long userId) {
        return userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + userId));
    }

    public TDUser createUser(TDUser user) {
        if (user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty.");
        }
        return userRepository.save(user);
    }

    public TDUser updateUser(Long userId, TDUser user) {
        TDUser existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found for update."));

        if (userId == null || user == null) {
            throw new IllegalArgumentException("User ID and User object must not be null");
        }

        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        existingUser.setPassword(user.getPassword());

        return userRepository.save(existingUser);
    }

    public void deleteUser(Long userId) {
        TDUser existingUser = userRepository.findById(userId)
            .orElseThrow(() -> new ResourceNotFoundException("User with ID " + userId + " not found for deletion."));
        
        userRepository.delete(existingUser);
    }


}
