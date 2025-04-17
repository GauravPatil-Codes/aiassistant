package com.udyamsarathi.aiassistant.services;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.udyamsarathi.aiassistant.entities.User;
import com.udyamsarathi.aiassistant.repositories.UserRepository;
import java.util.Optional;
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    
    @Override
    public User saveUser(User user) {
    	return userRepository.save(user);

    }
  
    @Override
    public User updateLocation(String id, String location) {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()) {
            User user = userOptional.get();
            user.setLocation(location);
            return userRepository.save(user);
        }
        throw new RuntimeException("User not found with ID: " + id);
    }

    @Override
    public User updateBusinessType(String conversationId, String businessType) {
        User user = userRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        User.BusinessPreference preference = user.getBusinessPreference();
        if (preference == null) {
            preference = new User.BusinessPreference();
        }
    
        preference.setType(businessType);
        user.setBusinessPreference(preference);
        user.setUpdatedAt(new Date());
    
        return userRepository.save(user);
    }

    
    @Override
    public User updateBusiness(String conversationId, String specificBusiness) {
        User user = userRepository.findById(conversationId)
                .orElseThrow(() -> new RuntimeException("User not found"));
    
        User.BusinessPreference preference = user.getBusinessPreference();
        if (preference == null) {
            preference = new User.BusinessPreference();
        }
    
        preference.setSpecificBusiness(specificBusiness);
        user.setBusinessPreference(preference);
        user.setUpdatedAt(new Date());
    
        return userRepository.save(user);
    }
    
    @Override
    public Optional<User> getUserById(String id) {
            	    return userRepository.findById(id);
    }
    
}
