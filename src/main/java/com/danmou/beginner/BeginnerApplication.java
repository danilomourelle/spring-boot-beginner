package com.danmou.beginner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.danmou.beginner.dao.AppDAO;
import com.danmou.beginner.entity.Instructor;
import com.danmou.beginner.entity.InstructorDetail;

@SpringBootApplication()
public class BeginnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeginnerApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
			createInstructor(appDAO);
		};
	}

	private void createInstructor(AppDAO appDAO) {
		Instructor instructor = new Instructor("Chad", "Darby", "darby@email.com");
		InstructorDetail instructorDetail = new InstructorDetail("Darbyed", "Run");

		instructor.setInstructorDetail(instructorDetail);

		appDAO.save(instructor);
	}
}
