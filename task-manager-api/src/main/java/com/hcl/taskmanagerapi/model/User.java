package com.hcl.taskmanagerapi.model;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.Set;


@Entity
public class User {
  @OneToMany(mappedBy="user", fetch=FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<Task> tasks;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "username", unique = true, nullable = false)
  @NotNull(message = "Username cannot be null")
  @Size(min = 2, max = 20, message = "Username must be between 2 and 20 characters")
  private String username;

  @Column(name = "password", nullable = false)
  @NotNull(message = "Password cannot be null")
  @Size(min = 8, message = "Password must be at least 8 characters")
  private String password;

  @Column(name = "first_name")
  @NotNull(message = "First Name cannot be null")
  @Size(min = 2, message = "First Name must be at least 2 characters")
  private String firstName;

  @Column(name = "last_name")
  @NotNull(message = "Last Name cannot be null")
  @Size(min = 2, message = "Last Name must be at least 2 characters")
  private String lastName;

  @Column(name = "email")
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

  public Set<Task> getTasks() {
    return tasks;
  }
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

  @Override
  public String toString() {
    return "{" +
      id + ", " +
      username + ", " +
      firstName + ", " +
      lastName + ", " +
      email +"}";
  }
}

