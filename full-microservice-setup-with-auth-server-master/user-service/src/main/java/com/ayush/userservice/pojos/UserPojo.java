package com.ayush.userservice.pojos;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserPojo {
    private Long id;
    private String name;
    private String email;
    private String password;
}
