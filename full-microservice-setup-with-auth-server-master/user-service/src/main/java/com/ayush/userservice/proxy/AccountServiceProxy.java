package com.ayush.userservice.proxy;

import com.ayush.userservice.GlobalApiResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = AccountServiceProxy.SERVICE_NAME)
public interface AccountServiceProxy {

    String SERVICE_NAME="account-service";

    @GetMapping("/api/detail/user-id/{userId}")
    ResponseEntity<GlobalApiResponse> fetchAccountDetailByUserId(@PathVariable("userId") Long userId);
}
