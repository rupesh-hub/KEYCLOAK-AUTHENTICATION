package com.ayush.userservice.service.impl;

import com.ayush.userservice.entity.Account;
import com.ayush.userservice.entity.User;
import com.ayush.userservice.pojos.ResponsePojo;
import com.ayush.userservice.pojos.UserPojo;
import com.ayush.userservice.proxy.AccountServiceData;
import com.ayush.userservice.repo.UserRepo;
import com.ayush.userservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepo userRepo;
    private final AccountServiceData accountServiceData;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<ResponsePojo> getAccountDetailByUserId(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new RuntimeException("User not found")
        );
        List<Account> accountList = accountServiceData.fetchAccountDetailByUserId(userId);
        List<ResponsePojo> responsePojos=new ArrayList<>();
        for (Account a :accountList){
            responsePojos.add(
                    ResponsePojo.builder()
                            .id(a.getId())
                            .amountPaid(a.getAmountPaid())
                            .name(user.getName())
                            .build()
            );
        }
        return responsePojos;
    }

    @Override
    public UserPojo getUserDetailById(Long userId) {
        User user = userRepo.findById(userId).orElseThrow(
                () -> new RuntimeException("Student Not Found")
        );
        return UserPojo.builder()
                .id(user.getId())
                .name(user.getName())
                .build();
    }

    @Override
    public Long createUser(UserPojo userPojo) {
        User user= User.builder()
                .name(userPojo.getName())
                .email(userPojo.getEmail())
                .password(passwordEncoder.encode(userPojo.getPassword()))
                .accountNonExpired(true)
                .accountNonLocked(true)
                .active(true)
                .credentialsNonExpired(true)
                .build();
        User user1 = userRepo.save(user);
        return  user1.getId();
    }
}
