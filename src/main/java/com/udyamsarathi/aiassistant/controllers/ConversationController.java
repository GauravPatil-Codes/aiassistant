package com.udyamsarathi.aiassistant.controllers;

import dto.ApiResponse;
import com.udyamsarathi.aiassistant.entities.User;
import com.udyamsarathi.aiassistant.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    @Autowired
    private UserService userService;

    @PostMapping("/start")
    public ResponseEntity<ApiResponse<Map<String, Object>>> startConversation(@Valid @RequestBody User user) {
        User savedUser = userService.saveUser(user);

        Map<String, Object> data = new HashMap<>();
        data.put("conversationId", savedUser.getId());
        data.put("nextStep", "location");

        return ResponseEntity.ok(new ApiResponse<>(200, data));
    }

    @PutMapping("/{conversationId}/location")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateLocation(
            @PathVariable String conversationId,
            @RequestBody Map<String, String> body
    ) {
        try {
            String location = body.get("location");
            User updatedUser = userService.updateLocation(conversationId, location);

            Map<String, Object> data = new HashMap<>();
            data.put("conversationId", updatedUser.getId());
            data.put("location", updatedUser.getLocation());
            data.put("nextStep", "businessType");

            return ResponseEntity.ok(new ApiResponse<>(200, data));

        } catch (RuntimeException e) {
            Map<String, Object> data = new HashMap<>();
            data.put("Message", "No Content or Something Wents Wrong");

            return ResponseEntity.status(204).body(new ApiResponse<>(204, data));
        }
    }

    @PutMapping("/{conversationId}/business-type")
    public ResponseEntity<ApiResponse<Map<String, Object>>> updateBusinessType(
            @PathVariable String conversationId,
            @RequestBody Map<String, String> body
    ) {
        try {
            String businessType = body.get("businessType");
    
            User updatedUser = userService.updateBusinessType(conversationId, businessType);
    
            Map<String, Object> data = new HashMap<>();
            data.put("conversationId", updatedUser.getId());
            data.put("businessType", updatedUser.getBusinessPreference().getType());
            data.put("nextStep", "specificBusiness");
    
            return ResponseEntity.ok(new ApiResponse<>(200, data));
    
        } catch (Exception e) {
            Map<String, Object> errorData = new HashMap<>();
            errorData.put("msg", "User not found or update failed");
    
            return ResponseEntity.status(204).body(new ApiResponse<>(204, errorData));
        }
    }
    

    @PutMapping("/{conversationId}/specific-business")
public ResponseEntity<ApiResponse<Map<String, Object>>> updateSpecificBusiness(
        @PathVariable String conversationId,
        @RequestBody Map<String, String> body
) {
    try {
        String specificBusiness = body.get("specificBusiness");

        User updatedUser = userService.updateBusiness(conversationId, specificBusiness);

        Map<String, Object> data = new HashMap<>();
        data.put("conversationId", updatedUser.getId());
        data.put("specificBusiness", updatedUser.getBusinessPreference().getSpecificBusiness());

        return ResponseEntity.ok(new ApiResponse<>(200, data));

    } catch (Exception e) {
        Map<String, Object> errorData = new HashMap<>();
        errorData.put("msg", "User not found or update failed");

        return ResponseEntity.status(204).body(new ApiResponse<>(204, errorData));
    }
}

}
