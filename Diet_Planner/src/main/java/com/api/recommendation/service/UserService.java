package com.api.recommendation.service;

import com.api.recommendation.dto.RegisterRequest;
import com.api.recommendation.entity.User;
import com.api.recommendation.repository.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class UserService {
    private final Userrepo userrepo;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JWTServices jwtServices;

    private BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(12);

//    public UserService(PasswordEncoder passwordEncoder) {
//        this.passwordEncoder = passwordEncoder;
//    }

    public UserService(Userrepo userrepo, PasswordEncoder passwordEncoder) {
        this.userrepo = userrepo;
    }

    public User register(RegisterRequest request, boolean passwordencoded) {
        User user = new User();
        user.setUserName(request.getUserName());
        user.setEmail(request.getEmail());
        if(!passwordencoded) {
            user.setPassword(encoder.encode(request.getPassword()));
        }
        user.setRoles(Arrays.asList("USER"));
        return userrepo.save(user);
    }

    public User findByUserName(String username){
        return userrepo.findByUserName(username);
    }

    public String verifyuser(User user) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword()));

        if(authentication.isAuthenticated()){
            return jwtServices.generatejwttoken(user.getUserName());
        }
        return "fail";
    }

//    public User authenticate(LoginRequest request) {
//        return Userrepo.findByUsername(request.getUsername())
//                .filter(user -> passwordEncoder.matches(request.getPassword(), user.getPassword()))
//                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
//    }
}

