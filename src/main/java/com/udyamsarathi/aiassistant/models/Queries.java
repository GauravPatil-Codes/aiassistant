package com.udyamsarathi.aiassistant.models;

import java.util.Date;
import java.util.List;
import java.util.ArrayList;

public class Queries {
    private List<QueryItem> items;
    
    public Queries() {
        this.items = new ArrayList<>();
    }
    
    public void addQuery(String question, String answer) {
        QueryItem item = new QueryItem(question, answer, new Date());
        items.add(item);
    }
    
    public List<QueryItem> getItems() {
        return items;
    }
    
    public void setItems(List<QueryItem> items) {
        this.items = items;
    }
    
    // Inner class to represent a single query-answer pair
    public static class QueryItem {
        private String question;
        private String answer;
        private Date timestamp;
        
        public QueryItem() {
        }
        
        public QueryItem(String question, String answer, Date timestamp) {
            this.question = question;
            this.answer = answer;
            this.timestamp = timestamp;
        }
        
        // Getters and setters
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
}