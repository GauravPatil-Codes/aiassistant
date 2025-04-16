package com.udyamsarathi.aiassistant.dtos;

public class ApiResponse {
    private String status;
    private Object data;
    
    public ApiResponse(String status, Object data) {
        this.status = status;
        this.data = data;
    }
    
    // Getters and setters
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public Object getData() {
        return data;
    }
    
    public void setData(Object data) {
        this.data = data;
    }
}


