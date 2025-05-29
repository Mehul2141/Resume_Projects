package com.api.recommendation.service;

import com.api.recommendation.entity.User;
import com.api.recommendation.repository.Userrepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class UserDetailsServiceImp implements UserDetailsService {
    @Autowired
    private Userrepo userDetailsRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userDetails = userDetailsRepository.findByUserName(username);

        if (userDetails == null) {
            throw new UsernameNotFoundException("User with username: " + username + " not found");
        }

        // Create and return the Spring Security UserDetails object
        return org.springframework.security.core.userdetails.User.builder()
                .username(userDetails.getUserName())
                .password(userDetails.getPassword())
                .roles(userDetails.getRoles().toArray(new String[0])) // Replace with appropriate roles from userDetails if available
                .build();
    }
}
