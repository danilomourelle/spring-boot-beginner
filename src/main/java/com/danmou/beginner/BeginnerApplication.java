package com.danmou.beginner;

import java.util.List;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.danmou.beginner.dao.AppDAO;
import com.danmou.beginner.entity.Course;
import com.danmou.beginner.entity.Instructor;
import com.danmou.beginner.entity.InstructorDetail;
import com.danmou.beginner.entity.Review;
import com.danmou.beginner.entity.Student;

@SpringBootApplication()
public class BeginnerApplication {

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
			// findInstructorWithCourses(appDAO);
			// findInstructorWithCoursesJoinFetch(appDAO);
			// updateInstructor(appDAO);
			// updateCourse(appDAO);
			// deleteCourse(appDAO);
			// createCourseWithReviews(appDAO);
			// retrieveCourseWithReviews(appDAO);
			// deleteCourseWithReviews(appDAO);
			createCourseAndStudents(appDAO);
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
		int id = 13;

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

	private void findInstructorWithCourses(AppDAO appDAO) {
		int id = 10;
		Instructor instructor = appDAO.findInstructorById(id);
		List<Course> courses = appDAO.findCoursesByInstructorId(instructor.getId());

		System.out.println("Before: " + instructor.getCourses().getClass());
		instructor.setCourses(courses);
		System.out.println("After: " + instructor.getCourses().getClass());

		// System.out.println("Instructor: " + instructor);
		// System.out.println("Associated courses: " + instructor.getCourses());

		/**
		 * Podemos reparar que o tipo do campo "courses" não é mais uma List, mas sim um
		 * tipo de Hibernate.
		 * Esse tipo, quando tem uma sessão aberta com o banco de dados vai se comportar
		 * como uma lista,
		 * mas nos casos em que a sessão já foi encerrada, ele vai dar o erro e
		 * LazyException ao tentar manipulá-lo.
		 * Então utilizar o método "addCourse" não vai funcionar, e por isso foi preciso
		 * criar o método "setCourse",
		 * assim a gente consegue simplesmente sobrescrever o campo inteiro, alternando
		 * inclusive a tipagem.
		 */
	}

	private void findInstructorWithCoursesJoinFetch(AppDAO appDAO) {
		int id = 10;
		Instructor instructor = appDAO.findInstructorByIdJoinFetch(id);

		System.out.println("Instructor: " + instructor);
		System.out.println("Associated courses: " + instructor.getCourses());
	}

	private void updateInstructor(AppDAO appDAO) {
		int id = 10;
		Instructor instructor = appDAO.findInstructorById(id);

		instructor.setLastName("Tester");

		appDAO.updateInstructor(instructor);
	}

	private void updateCourse(AppDAO appDAO) {
		int courseId = 3;
		int instructorId = 7;
		
		/**
		 * Once course has a many-to-one relation with Instructor,
		 * it has a eager fetch type.
		 * So in this case, the select for course table
		 * will also have a join call to instructor table
		 */
		Course course = appDAO.findCourseById(courseId);
		System.out.println(course.getInstructor());

		Instructor instructor = appDAO.findInstructorById(instructorId);

		instructor.setFirstName("Changed");

		course.setTitle("Mahjong - The relaxing game");
		course.setInstructor(instructor);

		appDAO.updateCourse(course);
		/**
		 * How update a instructor related to a course, and save the course will also
		 * save the instructor?
		 * This happens because de cascade config on entities.
		 * 
		 * But why it does a select twice??
		 * Because ORM always do a select before a update.
		 * So in this case we have a select for the find method, and a seconde select
		 * when updating.
		 * This is because they check all the values of the entity to really set a good
		 * update query.
		 * We can notice that run updateCourse twice will produce a update query at
		 * first but not at second.
		 */
	}

	private void deleteCourse(AppDAO appDAO) {
		int courseId = 3;

		appDAO.deleteCourseById(courseId);
	}

	private void createCourseWithReviews(AppDAO appDAO) {
		Review review1 = new Review("Top 10");
		Review review2 = new Review("Could not be better");

		Course course = new Course("JavaScript: The best programming language");

		course.addReview(review1);
		course.addReview(review2);

		appDAO.saveCourse(course);
	}

	private void retrieveCourseWithReviews(AppDAO appDAO) {
		int courseId = 1;
		Course course = appDAO.findCourseAndReviewsByCourseId(courseId);

		System.out.println(course);

		System.out.println(course.getReviews());
	}

	private void deleteCourseWithReviews(AppDAO appDAO) {
		int courseId = 1;
		appDAO.deleteCourseById(courseId);
	}

	private void createCourseAndStudents(AppDAO appDAO) {
		Course course = new Course("NodeJS: The game changer for JavaScript");

		Student student1 =  new Student("Danilo", "Mourelle", "danilomourelle@email.com");
		Student student2 =  new Student("Mary", "Markle", "marymarkle@email.com");

		course.addStudent(student1);
		course.addStudent(student2);

		System.out.println(course);
		System.out.println(course.getStudents());

		appDAO.saveCourse(course);
	}

	private void retrieveCourseWithStudents(AppDAO appDAO) {
		int courseId = 3;
		Course course = appDAO.findCourseAndStudentsByCourseId(courseId);

		System.out.println(course);
		System.out.println(course.getStudents());
	}

	private void findStudentAndCourses(AppDAO appDAO) {
		int studentId = 2;
		Student student = appDAO.findStudentAndCoursesByStudentId(studentId);

		System.out.println(student);
		System.out.println(student.getCourses());
	}
}
