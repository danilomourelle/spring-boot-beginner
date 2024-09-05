package com.danmou.beginner.entity;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity
@Table(name = "course")
public class Course {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "title")
  private String title;

  @ManyToOne(cascade = { CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH })
  @JoinColumn(name = "instructor_id")
  private Instructor instructor;

  /**
   * Since it will be a unidirectional relation, 
   * the JoinColumn will stay here despite the foreign key exists in review table.
   * But this will have a side effect. The hibernate will first insert the review
   * them it will update it to set the course_id, even though it already has course_id info when inserting review.
   * 
   * One way to prevent this, is the bidirectional relation,
   * and updating the 'addReview' method to add review to list
   * but also set the current course (this) to review.
   */
  @OneToMany(cascade = CascadeType.ALL)
  @JoinColumn(name = "course_id")
  List<Review> reviews = new ArrayList<>();

  public Course() {
  }

  public Course(String title) {
    this.title = title;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public Instructor getInstructor() {
    return instructor;
  }

  public void setInstructor(Instructor instructor) {
    this.instructor = instructor;
  }

  public List<Review> getReviews() {
    return reviews;
  }

  public void setReviews(List<Review> reviews) {
    this.reviews = reviews;
  }

  public void addReview(Review review) {
    reviews.add(review);
  }

  @Override
  public String toString() {
    return "Course [id=" + id + ", title=" + title + "]";
  }
}
