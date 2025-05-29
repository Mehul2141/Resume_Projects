package com.api.recommendation.controller;

import com.api.recommendation.dto.RegisterRequest;
import com.api.recommendation.entity.User;
import com.api.recommendation.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) {
        try{
            User user = userService.register(request, false);
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
    public String loginrequest(@RequestBody User user){
        return userService.verifyuser(user);
    }

    @GetMapping("/getuser")
    public ResponseEntity<User> getuserbyusername(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        User entry = userService.findByUserName(username);
        if(entry != null){
            return new ResponseEntity<>(entry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}

