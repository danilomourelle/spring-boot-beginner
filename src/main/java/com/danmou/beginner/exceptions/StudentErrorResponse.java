package com.danmou.beginner.exceptions;

public class StudentErrorResponse {
  private Integer status;
  private String message;
  private Long timestamp;

  public StudentErrorResponse() {
  }

  public StudentErrorResponse(Integer status, String message, Long timestamp) {
    this.status = status;
    this.message = message;
    this.timestamp = timestamp;
  }

  public Integer getStatus() {
    return status;
  }

  public void setStatus(Integer status) {
    this.status = status;
  }

  public String getMessage() {
    return message;
  }

  public void setMessage(String message) {
    this.message = message;
  }

  public Long getTimestamp() {
    return timestamp;
  }

  public void setTimestamp(Long timestamp) {
    this.timestamp = timestamp;
  }

}
