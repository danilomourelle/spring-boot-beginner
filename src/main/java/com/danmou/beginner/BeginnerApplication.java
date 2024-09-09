package com.danmou.beginner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.danmou.beginner.dao.AccountDAO;
import com.danmou.beginner.dao.MembershipDAO;

@SpringBootApplication()
public class BeginnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeginnerApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AccountDAO accountDAO, MembershipDAO membershipDAO) {
		return runner -> {
			demoOfBeforeAdvice(accountDAO, membershipDAO);
		};
	}

	private void demoOfBeforeAdvice(AccountDAO accountDAO, MembershipDAO membershipDAO) {
		System.out.println("Common method");
		accountDAO.addAccount();
		
		System.out.println("\nGetter");
		accountDAO.getBar();
		
		System.out.println("\nSetter");
		accountDAO.setBar("null");
	}
}
