package com.javaadr.renderapi.Service;

import com.javaadr.renderapi.Entity.User;
import com.javaadr.renderapi.Repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findById(UUID id){
        return userRepository.findById(id);
    }

    public User saveUser(User toSave) {
        String hashedPassword = passwordEncoder.encode(toSave.getPassword());
        toSave.setPassword(hashedPassword);
        return userRepository.save(toSave);
    }

    public User authenticate(String email, String password) {
        User user = userRepository.findByEmail(email);

        if (user != null && passwordEncoder.matches(password, user.getPassword())) {
            return user;
        }

        return null;
    }
}
