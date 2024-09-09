package com.danmou.beginner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.danmou.beginner.dao.AccountDAO;

@SpringBootApplication()
public class BeginnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeginnerApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AccountDAO accountDAO) {
		return runner -> {
			demoOfBeforeAdvice(accountDAO);
		};
	}

	private void demoOfBeforeAdvice(AccountDAO accountDAO) {
		accountDAO.addAccount();

		System.out.println("\ndoing it again\n");

		accountDAO.addAccount();
	}
}
