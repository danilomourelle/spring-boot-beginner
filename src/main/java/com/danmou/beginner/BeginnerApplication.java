package com.danmou.beginner;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.danmou.beginner.dao.AccountDAO;
import com.danmou.beginner.dao.MembershipDAO;
import com.danmou.beginner.entity.Account;

@SpringBootApplication()
public class BeginnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeginnerApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AccountDAO accountDAO, MembershipDAO membershipDAO) {
		return runner -> {
			// demoOfBeforeAdvice(accountDAO, membershipDAO);
			// demoAfterReturningAdvice(accountDAO);
			demoAfterThrowingAdvice(accountDAO);
		};
	}

	private void demoAfterThrowingAdvice(AccountDAO accountDAO) {
		System.out.println("Leaving Main");
		try {
			Account account = accountDAO.findAccountByUsername();
		} catch (Exception e) {
			System.out.println("Back to Main");
			System.out.println(e);
		}

	}

	private void demoAfterReturningAdvice(AccountDAO accountDAO) {
		System.out.println("Leaving Main");
		List<Account> accounts = accountDAO.findAccounts();

		System.out.println("Back to Main");
		System.out.println(accounts);
	}

	private void demoOfBeforeAdvice(AccountDAO accountDAO, MembershipDAO membershipDAO) {
		accountDAO.addAccount(new Account("Foo", "Bar"));

		accountDAO.getBar();

		accountDAO.setBar("null");

		membershipDAO.addMember();
	}
}
