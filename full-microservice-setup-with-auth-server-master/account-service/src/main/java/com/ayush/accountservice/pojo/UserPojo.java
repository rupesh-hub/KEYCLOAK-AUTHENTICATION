package com.ayush.accountservice.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPojo {
    private Long id;
    private String name;
}
