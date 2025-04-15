package com.udyamsarathi.aiassistant.services;

import com.udyamsarathi.aiassistant.entities.User;

public interface UserService {
    User saveUser(User user);
    User updateLocation(String id, String location);
    User updateBusinessType(String id, String type);
    User updateBusiness(String conversationId, String specificBusiness);
}
