package com.ayush.accountservice.controller;

import com.ayush.accountservice.GlobalApiResponse;
import com.fasterxml.jackson.databind.ObjectMapper;

public class BaseController {

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