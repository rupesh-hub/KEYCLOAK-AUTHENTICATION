package com.ayush.accountservice.entity;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "account")
public class Account {

    @Id
    @SequenceGenerator(name = "account_seq",sequenceName = "account_seq",allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE,generator = "account_seq")
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "amount_paid")
    private Double amountPaid;
}
