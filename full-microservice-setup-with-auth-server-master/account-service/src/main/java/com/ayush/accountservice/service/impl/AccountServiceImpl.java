package com.ayush.accountservice.service.impl;

import com.ayush.accountservice.entity.Account;
import com.ayush.accountservice.pojo.AccountDetail;
import com.ayush.accountservice.pojo.ResponsePojo;
import com.ayush.accountservice.pojo.UserPojo;
import com.ayush.accountservice.proxy.data.UserServiceData;
import com.ayush.accountservice.repo.AccountRepo;
import com.ayush.accountservice.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepo accountRepo;
    private final UserServiceData userServiceData;

    @Override
    public ResponsePojo getAccountDetailByUserId(Long userId) {
        UserPojo userPojo = userServiceData.getUserById(userId);
        List<Account> accountList = accountRepo.findByUserId(userId);
        ResponsePojo responsePojo = ResponsePojo.builder()
                .id(userPojo.getId())
                .name(userPojo.getName())
                .accountDetail(
                        accountList.parallelStream().map(x -> {
                            return AccountDetail.builder()
                                    .id(x.getId())
                                    .amountPaid(x.getAmountPaid())
                                    .build();
                        }).collect(Collectors.toList())
                )
                .build();
        return responsePojo;
    }
}
