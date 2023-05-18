package com.ayush.userservice.controller;

import com.ayush.userservice.GlobalApiResponse;
import com.ayush.userservice.pojos.UserPojo;
import com.ayush.userservice.repo.UserRepo;
import com.ayush.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends BaseController {

    private final UserService userService;

    @GetMapping("/account-details/{userId}")
    private ResponseEntity fetchAccountDetailByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(
                successResponse("Account detail fetched",userService.getAccountDetailByUserId(userId))
        );
    }

    @GetMapping("/id/{userId}")
    private ResponseEntity<GlobalApiResponse> fetchUserByUserId(@PathVariable("userId") Long userId){
        return ResponseEntity.ok(
                successResponse("Account detail fetched",userService.getUserDetailById(userId))
        );
    }

    @PostMapping("/register")
    private ResponseEntity<GlobalApiResponse> createUser(@RequestBody UserPojo userPojo){
        return ResponseEntity.ok(
                successResponse("User Created Successfully",userService.createUser(userPojo))
        );
    }

}
