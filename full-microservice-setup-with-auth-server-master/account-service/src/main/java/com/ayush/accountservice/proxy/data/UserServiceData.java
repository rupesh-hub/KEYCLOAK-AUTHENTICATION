package com.ayush.accountservice.proxy.data;

import com.ayush.accountservice.GlobalApiResponse;
import com.ayush.accountservice.pojo.UserPojo;
import com.ayush.accountservice.proxy.UserServiceProxy;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserServiceData {
    private final UserServiceProxy userServiceProxy;
    private final ObjectMapper objectMapper;

    public UserPojo getUserById(Long userId){
        ResponseEntity<GlobalApiResponse> responseEntity = userServiceProxy.fetchUserByUserId(userId);
        GlobalApiResponse globalApiResponse = objectMapper.convertValue(responseEntity.getBody(), GlobalApiResponse.class);
        UserPojo userPojo = objectMapper.convertValue(globalApiResponse.getData(), UserPojo.class);
        return userPojo;
    }
}
