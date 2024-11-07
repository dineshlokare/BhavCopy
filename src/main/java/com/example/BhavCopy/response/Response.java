package com.example.BhavCopy.response;

public class Response {
    private String message;
    private String status;

    // Constructor
    public Response(String message, String status) {
        this.message = message;
        this.status = status;
    }

    // Getters and Setters
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String isSuccess() {
        return status;
    }

    public void setSuccess(String status) {
        this.status = status;
    }
}
