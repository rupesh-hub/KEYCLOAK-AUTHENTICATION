package com.ayush.userservice.controller;

import com.ayush.userservice.GlobalApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseController {

    private ObjectMapper objectMapper=new ObjectMapper();

    public GlobalApiResponse successResponse(String message, Object data){
        GlobalApiResponse globalApiResponse=new GlobalApiResponse();
        globalApiResponse.setMessage(message);
        globalApiResponse.setData(data);
        return globalApiResponse;
    }

    public GlobalApiResponse errorResponse(String message,Object data){
        GlobalApiResponse globalApiResponse=new GlobalApiResponse();
        globalApiResponse.setMessage(message);
        globalApiResponse.setData(data);
        return globalApiResponse;
    }
}