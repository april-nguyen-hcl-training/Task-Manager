package com.hcl.taskmanagerwebapp.model;

import javax.validation.constraints.*;
import java.util.Set;

public class User {

  private Set<Task> tasks;

  private int id;

  @NotNull(message = "Username cannot be null")
  @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
  private String username;

  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;

  @NotNull(message = "First Name cannot be null")
  @Size(min = 2, message = "First Name must be at least 2 characters")
  private String firstName;

  @NotNull(message = "Last Name cannot be null")
  @Size(min = 2, message = "Last Name must be at least 2 characters")
  private String lastName;

  @Email(regexp=".*@.*\\..*", message = "Email should be valid")
  private String email;

  public User() {}

  public User(String username, String password, String firstName, String lastName, String email) {
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public User(int id, String username, String password, String firstName, String lastName, String email) {
    this.id = id;
    this.username = username;
    this.password = password;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
  }

  public Set<Task> getTasks() { return tasks; }
  public void setTasks(Set<Task> tasks) {
    this.tasks = tasks;
  }

  public int getId() {
    return id;
  }
  public void setId(int id) {
    this.id = id;
  }

  public String getUsername() {
    return username;
  }
  public void setUsername(String username) {
    this.username = username;
  }

  public String getPassword() {
    return password;
  }
  public void setPassword(String password) {
    this.password = password;
  }

  public String getFirstName() {
    return firstName;
  }
  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }
  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }
  public void setEmail(String email) {
    this.email = email;
  }

}
