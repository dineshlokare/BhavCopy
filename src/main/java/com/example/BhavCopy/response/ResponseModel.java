package com.example.BhavCopy.response;

import java.util.LinkedHashMap;
import java.util.Map;

public class ResponseModel<T> {
     String status;
     String message;
     Object data;
    Map<String,Object> list =new LinkedHashMap<>();

    // Constructors
    public ResponseModel(String status, String message, Object data) {
        this.status = status;
        this.message = message;
        this.data = data;
        list.put("status",status);
        list.put("message",message);
        list.put("data",data);
    }

    // Getters and Setters
    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Map<String,Object> getData() {
       return list;
    }

    public void setData(T data) {
        this.data = data;
    }
}
