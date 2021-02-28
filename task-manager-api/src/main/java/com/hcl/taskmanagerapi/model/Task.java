package com.hcl.taskmanagerapi.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModelProperty;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
public class Task {
  @JsonIgnore
  @ManyToOne(fetch=FetchType.LAZY)
  @JoinColumn(name="user_id", nullable=false)
  private User user;

  @ApiModelProperty(notes = "The database generated task ID")
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private int id;

  @ApiModelProperty(notes = "The name of the task", required = true)
  @Size(min = 2, max = 20, message = "Name must be between 2 and 20 characters")
  @NotNull(message = "Name cannot be null")
  @Column(name = "name", nullable = false)
  private String name;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date start;

  @DateTimeFormat(pattern = "yyyy-MM-dd")
  private Date end;

  private String description;

  @Column(columnDefinition = "enum('HIGH','MEDIUM', 'LOW')")
  @Enumerated(EnumType.STRING)
  private Severity severity;

  public enum Severity {
    HIGH, MEDIUM, LOW
  }

  public Task() { }

  public User getUser() {
    return user;
  }
  public void setUser(User user) {
    this.user = user;
  }

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }
  public void setName(String name) {
    this.name = name;
  }

  public Date getStart() {
    return start;
  }
  public void setStart(Date start) {
    this.start = start;
  }

  public Date getEnd() {
    return end;
  }
  public void setEnd(Date end) {
    this.end = end;
  }

  public String getDescription() {
    return description;
  }
  public void setDescription(String description) {
    this.description = description;
  }

  public Severity getSeverity() {
    return severity;
  }
  public void setSeverity(Severity severity) {
    this.severity = severity;
  }

  @Override
  public String toString() {
    return "{" +
      name + ", " +
      description + ", " +
      start + ", " +
      end + ", " +
      severity + ", " +
      user +"}";
  }
}
