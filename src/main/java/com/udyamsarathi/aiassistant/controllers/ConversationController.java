package com.udyamsarathi.aiassistant.controllers;

import dto.ApiResponse;
import com.udyamsarathi.aiassistant.entities.User;
import com.udyamsarathi.aiassistant.repositories.UserRepository;
import com.udyamsarathi.aiassistant.services.UserService;
import jakarta.validation.Valid;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/conversation")
public class ConversationController {

    @Autowired
    private UserService userService;
    
    @Autowired
    private UserRepository userRepository;
    
    private final ChatClient chatClient;

    public ConversationController(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }


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
    
    
    @PostMapping("/askai")
    public ResponseEntity<?> askAI(
            @RequestParam String question,
            @RequestParam String conversationId
    ) {
        try {
            // Retrieve user information based on the conversationId
            Optional<User> userOptional = userService.getUserById(conversationId);
            
            if (!userOptional.isPresent()) {
                return ResponseEntity.badRequest().body("User not found for the given conversationId");
            }
            
            User user = userOptional.get();
            
            // Construct a prompt that includes user context
            StringBuilder contextualPrompt = new StringBuilder();
            contextualPrompt.append("User Context:\n");
            
            // Add user name if available
            if (user.getName() != null && !user.getName().isEmpty()) {
                contextualPrompt.append("Name: ").append(user.getName()).append("\n");
            }
            
            // Add user location if available
            if (user.getLocation() != null && !user.getLocation().isEmpty()) {
                contextualPrompt.append("Location: ").append(user.getLocation()).append("\n");
            }
            
            // Add language preference if available
            if (user.getLanguage() != null && !user.getLanguage().isEmpty()) {
                contextualPrompt.append("Preferred Language: ").append(user.getLanguage()).append("\n");
            }
            
            // Add business preferences if available
            if (user.getBusinessPreference() != null) {
                User.BusinessPreference bizPref = user.getBusinessPreference();
                
                if (bizPref.getType() != null && !bizPref.getType().isEmpty()) {
                    String businessType = bizPref.getType();
                    contextualPrompt.append("Business Type: ").append(businessType);
                    
                    // Add user-friendly description of business type
                    if (businessType.equals("FARM")) {
                        contextualPrompt.append(" (Agriculture/Farming)");
                    } else if (businessType.equals("NON_FARM")) {
                        contextualPrompt.append(" (Non-Agricultural Business)");
                    }
                    contextualPrompt.append("\n");
                }
                
                if (bizPref.getSpecificBusiness() != null && !bizPref.getSpecificBusiness().isEmpty()) {
                    contextualPrompt.append("Specific Business: ").append(bizPref.getSpecificBusiness()).append("\n");
                }
            }
            
            // Add previous conversation history for context (up to 3 most recent Q&As)
            if (user.getQueries() != null && !user.getQueries().isEmpty()) {
                List<User.Query> previousQueries = user.getQueries();
                int historyCount = Math.min(previousQueries.size(), 3); // Limit to 3 most recent exchanges
                
                if (historyCount > 0) {
                    contextualPrompt.append("\nRecent Conversation History:\n");
                    
                    // Get the most recent queries (up to 3)
                    List<User.Query> recentQueries = previousQueries.subList(
                        previousQueries.size() - historyCount, 
                        previousQueries.size()
                    );
                    
                    for (int i = 0; i < recentQueries.size(); i++) {
                        User.Query query = recentQueries.get(i);
                        contextualPrompt.append("Q: ").append(query.getQuestion()).append("\n");
                        contextualPrompt.append("A: ").append(query.getAnswer()).append("\n\n");
                    }
                }
            }
            
            // Add the current user's question
            contextualPrompt.append("\nCurrent User Question: ").append(question).append("\n\n");
            
            // Add instructions for the AI
            contextualPrompt.append("Please provide a helpful response based on the above context and question. ");
            contextualPrompt.append("Focus on providing information relevant to the user's location and business interests. ");
            
            if (user.getLanguage() != null && !user.getLanguage().isEmpty() && !user.getLanguage().equalsIgnoreCase("english")) {
                contextualPrompt.append("Respond in ").append(user.getLanguage()).append(" language. ");
            }
            
            // Call the AI with the enhanced prompt
            ChatResponse aiResponse = chatClient.prompt()
                    .user(contextualPrompt.toString())
                    .call()
                    .chatResponse();
            
            // Extract the AI's answer as a string (adjust based on your ChatResponse structure)
            String aiAnswer = aiResponse.toString();
            
           
            
            // Create a new query object to store in the user's history
            User.Query newQuery = new User.Query();
            newQuery.setQuestion(question);
            newQuery.setAnswer(aiAnswer);
            newQuery.setTimestamp(new Date());
            
            // Update the user's query history
            addQueryToUser(user, newQuery);
            
            // Return the AI response
            return ResponseEntity.ok(aiResponse);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to get AI response: " + e.getMessage());
        }
    }

    // Private helper method to add a query to a user and save it
    private User addQueryToUser(User user, User.Query query) {
        // Initialize queries list if null
        if (user.getQueries() == null) {
            user.setQueries(new ArrayList<>());
        }
        
        // Add the new query
        user.getQueries().add(query);
        
        // Update timestamp
        user.setUpdatedAt(new Date());
                // Save and return the updated user
        return userRepository.save(user);
    }
}
