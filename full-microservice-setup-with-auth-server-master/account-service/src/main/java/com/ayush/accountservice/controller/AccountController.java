package com.ayush.accountservice.controller;

import com.ayush.accountservice.GlobalApiResponse;
import com.ayush.accountservice.repo.AccountRepo;
import com.ayush.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/detail")
@RequiredArgsConstructor
public class AccountController extends BaseController {

    private final AccountService accountService;

    @GetMapping("/user-id/{userId}")
    private ResponseEntity<GlobalApiResponse> fetchAccountDetailByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(
                successResponse("Account detail fetched",accountService.getAccountDetailByUserId(userId))
        );
    }

    @GetMapping()
    private ResponseEntity<GlobalApiResponse> helloWorld(){
        return ResponseEntity.ok(
                successResponse("Hello World",null)
        );
    }

}
