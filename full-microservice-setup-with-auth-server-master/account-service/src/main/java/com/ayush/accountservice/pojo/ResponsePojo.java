package com.ayush.accountservice.pojo;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
@Builder
public class ResponsePojo {
    private Long id;
    private String name;
    private List<AccountDetail> accountDetail;
}
