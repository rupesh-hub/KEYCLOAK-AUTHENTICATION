package com.ayush.userservice.service;

import java.util.*;
import com.ayush.userservice.pojos.ResponsePojo;
import com.ayush.userservice.pojos.UserPojo;

public interface UserService {
    List<ResponsePojo> getAccountDetailByUserId(Long userId);

    UserPojo getUserDetailById(Long userId);

    Long createUser(UserPojo userPojo);
}
