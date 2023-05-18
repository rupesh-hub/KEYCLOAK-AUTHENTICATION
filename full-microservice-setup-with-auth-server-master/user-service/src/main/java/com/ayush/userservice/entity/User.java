package com.ayush.userservice.entity;

import lombok.*;

import javax.persistence.*;

@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private String address;

    private String email;

    private String password;

    private boolean active;

    private boolean accountNonExpired;
    private boolean credentialsNonExpired;
    private boolean accountNonLocked;
}
