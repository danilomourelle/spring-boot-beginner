package com.danmou.beginner;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.danmou.beginner.dao.AppDAO;
import com.danmou.beginner.entity.Course;
import com.danmou.beginner.entity.Instructor;
import com.danmou.beginner.entity.InstructorDetail;

@SpringBootApplication()
public class BeginnerApplication {

	@Autowired
	private InstructorService instructorService;

	@Autowired
	private AppDAO appDAO;

	public static void main(String[] args) {
		SpringApplication.run(BeginnerApplication.class, args);
	}

	@Bean
	CommandLineRunner commandLineRunner(AppDAO appDAO) {
		return runner -> {
			// createInstructor(appDAO);
			// findInstructor(appDAO);
			// deleteInstructor(appDAO);
			// findInstructorDetail(appDAO);
			// deleteInstructorDetail(appDAO);
			// createInstructorWithCourses(appDAO);
			findInstructorWithCourses();
		};
	}

	private void createInstructor(AppDAO appDAO) {
		Instructor instructor = new Instructor("Lowei", "Kim", "kim2@email.com");
		InstructorDetail instructorDetail = new InstructorDetail("Darbyed", "Read");

		instructor.setInstructorDetail(instructorDetail);

		appDAO.save(instructor);
	}

	private void findInstructor(AppDAO appDAO) {
		int id = 1;

		Instructor instructor = appDAO.findInstructorById(id);
		System.out.println("Instructor: " + instructor);
		System.out.println("Instructor Detail: " + instructor.getInstructorDetail());
	}

	private void deleteInstructor(AppDAO appDAO) {
		int id = 3;

		appDAO.deleteInstructorById(id);
	}

	private void findInstructorDetail(AppDAO appDAO) {
		int id = 1;

		InstructorDetail instructorDetail = appDAO.findInstructorDetailById(id);
		System.out.println("Instructor Detail: " + instructorDetail);
		System.out.println("Instructor: " + instructorDetail.getInstructor());
	}

	private void deleteInstructorDetail(AppDAO appDAO) {
		int id = 5;

		appDAO.deleteInstructorDetailById(id);
	}

	private void createInstructorWithCourses(AppDAO appDAO) {
		Instructor instructor = new Instructor("Mark", "Steffano", "staffno@email.com");
		InstructorDetail instructorDetail = new InstructorDetail("Stuffano", "Ride horse");
		instructor.setInstructorDetail(instructorDetail);

		Course course1 = new Course("Pinball Masterclass");
		Course course2 = new Course("Air Guitar - The Ultimate Guide");

		instructor.addCourse(course1);
		instructor.addCourse(course2);

		appDAO.save(instructor);
	}

	/*
	 * private void findInstructorWithCourses(AppDAO appDAO) {
	 * int id = 10;
	 * Instructor instructor = appDAO.findInstructorById(id);
	 * List<Course> courses = appDAO.findCoursesByInstructorId(instructor.getId());
	 * 
	 * instructor.setCourses(courses);
	 * 
	 * System.out.println("Instructor: " + instructor);
	 * System.out.println("Associated courses: " + instructor.getCourses());
	 * }
	 */

	private void findInstructorWithCourses() {
		int id = 10;
		Instructor instructor = instructorService.findInstructorWithCourses(id);

		// Print the type of the courses field before accessing it
		// System.out.println("Courses field type before accessing: " + instructor.getCourses());

		// Print the type of the courses field after accessing it
		// System.out.println("Courses field type after accessing: " + instructor.getCourses().getClass().getName());

		System.out.println("Instructor: " + instructor);
		System.out.println("Associated courses: " + instructor.getCourses());
	}
}
