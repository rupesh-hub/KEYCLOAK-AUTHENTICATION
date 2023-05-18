package com.ayush.accountservice.proxy;

import com.ayush.accountservice.GlobalApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Service
@FeignClient(name = UserServiceProxy.SERVICE_NAME)
public interface UserServiceProxy {
    String SERVICE_NAME="user-service";

    @GetMapping("/user/id/{userId}")
    ResponseEntity<GlobalApiResponse> fetchUserByUserId(@PathVariable("userId") Long userId);
}
