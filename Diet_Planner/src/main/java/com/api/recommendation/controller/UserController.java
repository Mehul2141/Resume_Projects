package com.api.recommendation.controller;

import com.api.recommendation.dto.LoginRequest;
import com.api.recommendation.dto.RegisterRequest;
import com.api.recommendation.entity.User;
import com.api.recommendation.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try{
            User user = userService.register(request);
            if(user == null){
                return ResponseEntity
                                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body("User registartion failed due to internal server error");
            }
            return ResponseEntity
                                .status(HttpStatus.CREATED)
                                .body(user);
        }
        catch (Exception e) {
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("An unexpected error occurred: " + e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        //User user = userService.authenticate(request);
        // For now, just return a placeholder token
        return ResponseEntity.ok("token-placeholder");
    }
}

