package com.ayush.accountservice.pojo;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDetail {
    private Long id;
    private Double amountPaid;
}
