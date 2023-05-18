package com.ayush.accountservice.repo;

import com.ayush.accountservice.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.*;

public interface AccountRepo extends JpaRepository<Account,Long> {

    @Query(
            nativeQuery=true,
            value="select * from account where user_id= ?1"
    )
    List<Account> findByUserId(Long userId);
}
