package com.danmou.beginner;

import java.util.List;

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
			// createStudent(studentDAO);
			// readStudent(studentDAO);
			// queryForStudents(studentDAO);
			// queryForStudentsByLastName(studentDAO);
			// updateStudent(studentDAO);
			deleteStudent(studentDAO);
		};
	}

	private void createStudent(IStudentDAO studentDAO) {
		System.out.println("Creating new student...");
		Student student = new Student("Danilo", "Mourelle", "danilomourelle@outlook.com");

		System.out.println("Saving student...");
		studentDAO.save(student);

		System.out.println("Save student. Generated id: " + student.getId());
	}

	private void readStudent(IStudentDAO studentDAO) {
		System.out.println("Finding for student ID: 2...");
		Student student = studentDAO.findById(2);

		System.out.println(student);
		;
	}

	private void queryForStudents(IStudentDAO studentDAO) {
		System.out.println("Querying all students...");
		List<Student> students = studentDAO.findAll();

		for (Student student : students) {
			System.out.println(student);
			;
		}
	}

	private void queryForStudentsByLastName(IStudentDAO studentDAO) {
		System.out.println("Querying all students with last name 'Garcia'");
		List<Student> students = studentDAO.findByLastName("Garcia");

		for (Student student : students) {
			System.out.println(student);
			;
		}
	}

	private void updateStudent(IStudentDAO studentDAO) {
		System.out.println("Retrieving student '1'...");
		Student student = studentDAO.findById(1);

		System.out.println("Updating first name...");
		student.setFirstName("Luigi");

		System.out.println("Saving update...");
		studentDAO.update(student);

		System.out.println(student);
	}

	private void deleteStudent(IStudentDAO studentDAO){
		System.out.println("Deleting student '3'...");
		studentDAO.delete(3);
	}
}
