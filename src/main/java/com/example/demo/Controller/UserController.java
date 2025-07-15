package com.example.demo.Controller;

import com.example.demo.Model.TDUser;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.Service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
    
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("{userID}")
    public ResponseEntity<TDUser> findUserById(@PathVariable Long userID) {
        TDUser user = userService.findById(userID);
        return new ResponseEntity<>(user, HttpStatus.OK); // 200 OK
    }

    @PostMapping
    public ResponseEntity<TDUser> createUser(@RequestBody TDUser user) {
        TDUser createdUser = userService.createUser(user);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED); // 201 Created
    }

    @PutMapping("{userID}")
    public ResponseEntity<TDUser> updateUser(@PathVariable Long userID, TDUser user) {
        TDUser updatedUser = userService.updateUser(userID, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK); // 200 OK
    }

    @DeleteMapping("{userID}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userID) {
        userService.deleteUser(userID);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
