package com.udyamsarathi.aiassistant.entities;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.Pattern;
// import javax.validation.constraints.NotBlank;
// import javax.validation.constraints.Size;
// import jakarta.validation.constraints.Pattern;
// import jakarta.validation.constraints.NotBlank;
// import jakarta.validation.Valid;

@Document(collection = "conversations")
public class User {

    @Id
    private String id;

    // @NotBlank(message = "Name is required")
    private String name;

    // @NotBlank(message = "Phone number is required")
    // @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be exactly 10 digits")
    private String phoneNumber;
  
    private String location; 
    private BusinessPreference businessPreference;
    private String language; 
    private List<Query> queries;
    private Date createdAt;
    private Date updatedAt;

    private String type;

    // Getters and Setters

    public static class BusinessPreference {
        private String type; // "FARM" or "NON_FARM"
        private String specificBusiness; // Business ID reference

        // Getters and Setters
        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getSpecificBusiness() {
            return specificBusiness;
        }

        public void setSpecificBusiness(String specificBusiness) {
            this.specificBusiness = specificBusiness;
        }
    }

    public static class Query {
        private String question;
        private String answer;
        private Date timestamp;

        // Getters and Setters
        public String getQuestion() {
            return question;
        }

        public void setQuestion(String question) {
            this.question = question;
        }

        public String getAnswer() {
            return answer;
        }

        public void setAnswer(String answer) {
            this.answer = answer;
        }

        public Date getTimestamp() {
            return timestamp;
        }

        public void setTimestamp(Date timestamp) {
            this.timestamp = timestamp;
        }
    }

    // Getters and Setters for main fields

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public BusinessPreference getBusinessPreference() {
        return businessPreference;
    }

    public void setBusinessPreference(BusinessPreference businessPreference) {
        this.businessPreference = businessPreference;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<Query> getQueries() {
        return queries;
    }

    public void setQueries(List<Query> queries) {
        this.queries = queries;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public void setType(String type) {
        this.type = type;
    }
}
