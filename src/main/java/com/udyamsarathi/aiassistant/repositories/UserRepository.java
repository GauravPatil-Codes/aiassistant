package com.udyamsarathi.aiassistant.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.udyamsarathi.aiassistant.entities.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
    boolean existsByPhoneNumber(String phoneNumber);
    User findByPhoneNumber(String phoneNumber);
}