package com.api.recommendation.repository;

import com.api.recommendation.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface Userrepo extends MongoRepository<User, String> {
    User findByUserName(String username);
    void deleteByUserName(String username);
}
