package com.example.accessing_data_rest.controllers;

import com.example.accessing_data_rest.model.User;
import com.example.accessing_data_rest.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/roborally/users")
public class UserController {
    @Autowired private UserService userService;

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
}
