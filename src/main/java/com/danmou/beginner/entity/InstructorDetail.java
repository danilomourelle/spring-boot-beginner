package com.danmou.beginner.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "instructor_detail")
public class InstructorDetail {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "youtube_channel")
  private String youtubeChannel;

  @Column(name = "hobby")
  private String hobby;

  public InstructorDetail() {
  }

  public InstructorDetail(String youtubeChannel, String hobby) {
    this.youtubeChannel = youtubeChannel;
    this.hobby = hobby;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getYoutubeChannel() {
    return youtubeChannel;
  }

  public void setYoutubeChannel(String youtubeChannel) {
    this.youtubeChannel = youtubeChannel;
  }

  public String getHobby() {
    return hobby;
  }

  public void setHobby(String hobby) {
    this.hobby = hobby;
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + id;
    return result;
  }

  @Override
  public boolean equals(Object obj) {
    if (this == obj)
      return true;
    if (obj == null)
      return false;
    if (getClass() != obj.getClass())
      return false;
    InstructorDetail other = (InstructorDetail) obj;
    if (id != other.id)
      return false;
    return true;
  }

  @Override
  public String toString() {
    return "InstructorDetail [id=" + id + ", youtubeChannel=" + youtubeChannel + ", hobby=" + hobby + "]";
  }
}
