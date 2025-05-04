package com.example.accessing_data_rest.services;

import com.example.accessing_data_rest.model.User;
import com.example.accessing_data_rest.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import java.util.List;

@Service
public class UserService {
    @Autowired private UserRepository userRepository;

    public List<User> getUsers() {
        return (List<User>) userRepository.findAll();
    }

    public List<User> searchUsers(String name) {
        System.out.println("Searching for users with name: " + name);
        var res = userRepository.findByName(name);
        System.out.println("Found users: " + res);
        return res;
    }

    @Transactional
    public User createUser(User user) {
        return userRepository.save(user);
    }
}
