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
			// demoAfterThrowingAdvice(accountDAO);
			// demoAfterAdvice(accountDAO);
			demoAroundAdvice(accountDAO);
		};
	}

	private void demoAroundAdvice(AccountDAO accountDAO) {
		System.out.println("Leaving Main");
		try {
			accountDAO.addAccount(new Account("Foo", "Bar"));
			List<Account> accounts = accountDAO.findAccounts();
			for (Account account : accounts) {
				System.out.println("main - response " + account);
			}
			Account account = accountDAO.findAccountByUsername();
			System.out.println("main - account that could failure " + account);
		} catch (Exception e) {
			System.out.println("Back to Main");
			System.out.println(e);
		}
	}

	private void demoAfterAdvice(AccountDAO accountDAO) {
		System.out.println("Leaving Main");
		try {
			List<Account> accounts = accountDAO.findAccounts();
			for (Account account : accounts) {
				System.out.println(account);
			}
			Account account = accountDAO.findAccountByUsername();
		} catch (Exception e) {
			System.out.println("Back to Main");
			System.out.println(e);
		}

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
