package com.example.accessing_data_rest.services;

import com.example.accessing_data_rest.model.User;
import com.example.accessing_data_rest.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

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

    public Optional<User> findByUsername(String username) {
        var users = userRepository.findByName(username);
        if (users == null || users.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(users.get(0));
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Transactional
    public User setOnline(Long id, boolean online) {
        User u = userRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        u.setOnline(online);
        return userRepository.save(u);
    }
}
