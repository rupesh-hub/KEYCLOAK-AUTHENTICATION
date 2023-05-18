package com.ayush.accountservice.service;

import com.ayush.accountservice.pojo.ResponsePojo;

public interface AccountService {
    ResponsePojo getAccountDetailByUserId(Long userId);
}
