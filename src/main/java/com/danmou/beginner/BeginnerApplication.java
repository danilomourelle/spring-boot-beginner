package com.danmou.beginner;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.danmou.beginner.dao.IStudentDAO;
import com.danmou.beginner.entities.Student;

@SpringBootApplication()
public class BeginnerApplication {

	public static void main(String[] args) {
		SpringApplication.run(BeginnerApplication.class, args);
	}

	@Bean
	public CommandLineRunner commandLineRunner(IStudentDAO studentDAO) {
		return runner -> { 
			createStudent(studentDAO);
		};
	}

	private void createStudent(IStudentDAO studentDAO) {
		System.out.println("Creating new student...");
		Student student = new Student("Danilo", "Mourelle", "danilomourelle@outlook.com");

		System.out.println("Saving student...");
		studentDAO.save(student);

		System.out.println("Save student. Generated id: " + student.getId());
	}
}
