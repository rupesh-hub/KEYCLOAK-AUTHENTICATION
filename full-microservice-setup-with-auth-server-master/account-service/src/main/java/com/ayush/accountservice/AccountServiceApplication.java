package com.ayush.accountservice;

import com.ayush.accountservice.entity.Account;
import com.ayush.accountservice.repo.AccountRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

import java.util.*;

@EnableFeignClients
@EnableEurekaClient
@SpringBootApplication
public class AccountServiceApplication implements CommandLineRunner {

	@Autowired
	private AccountRepo accountRepo;

	public static void main(String[] args) {
		SpringApplication.run(AccountServiceApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		List<Account> accountList=Arrays.asList(
				Account.builder().userId(1L).amountPaid(100.00).build(),
				Account.builder().userId(1L).amountPaid(200.00).build(),
				Account.builder().userId(2L).amountPaid(100.00).build(),
				Account.builder().userId(2L).amountPaid(300.00).build(),
				Account.builder().userId(2L).amountPaid(100.00).build()
		);

		accountRepo.saveAll(accountList);
	}
}
