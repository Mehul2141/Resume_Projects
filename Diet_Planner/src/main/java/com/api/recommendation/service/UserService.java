package com.api.recommendation.service;

import com.api.recommendation.dto.RegisterRequest;
import com.api.recommendation.entity.User;
import com.api.recommendation.repository.Userrepo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final Userrepo userrepo;
    private final PasswordEncoder passwordEncoder;

//    public UserService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    public UserService(Userrepo userrepo, PasswordEncoder passwordEncoder) {
        this.userrepo = userrepo;
        this.passwordEncoder = passwordEncoder;
    }

    public User register(RegisterRequest request) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        return userrepo.save(user);
    }

//    public User authenticate(LoginRequest request) {
//        return Userrepo.findByUsername(request.getUsername())
//                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
//                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
//    }
}

