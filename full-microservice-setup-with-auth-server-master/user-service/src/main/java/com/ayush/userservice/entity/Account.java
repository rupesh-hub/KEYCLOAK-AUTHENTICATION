package com.ayush.userservice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account {
    private Long id;
    private Long userId;
    private Double amountPaid;
}
