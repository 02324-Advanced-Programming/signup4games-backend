package com.example.accessing_data_rest.controllers;

import com.example.accessing_data_rest.model.User;
import com.example.accessing_data_rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/roborally/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("")
    public List<User> getAllUsers() {
        return userService.getUsers();
    }

    @GetMapping("/searchusers")
    public List<User> searchUsers(@RequestParam String name) {
        return userService.searchUsers(name);
    }

    @PostMapping(path = "",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public User postUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PostMapping(
            path = "/login",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public User login(@RequestParam("username") String username) {
        User u = userService.findByUsername(username)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "User not found"));
        return userService.setOnline(u.getUid(), true);
    }

    @PostMapping(path = "/logout", consumes = "application/json")
    public void logout(@RequestBody User user) {
        userService.setOnline(user.getUid(), false);
    }
}
