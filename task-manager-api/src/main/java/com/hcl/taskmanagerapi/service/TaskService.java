package com.hcl.taskmanagerapi.service;

import com.hcl.taskmanagerapi.exception.InvalidDataException;
import com.hcl.taskmanagerapi.exception.NotFoundException;
import com.hcl.taskmanagerapi.model.Task;
import com.hcl.taskmanagerapi.model.User;
import com.hcl.taskmanagerapi.repository.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

@Service
public class TaskService {
  private static final Logger log = LogManager.getLogger(TaskService.class);

  @Autowired
  private TaskRepository taskRepository;
  @Autowired
  private UserService userService;

  public List<Task> findByUsername(String username) {
    log.debug("Finding tasks with username: " + username);
    User user = userService.findByUsername(username);
    return this.taskRepository.findByUser(user);
  }

  public Task findById(Integer id) {
    log.debug("Finding task with id: " + id);
    return this.taskRepository.findById(id).orElseThrow(() -> new NotFoundException("Task not found!"));
  }

  public Task addTask(String username, Task task) {
    // Validate data inputs
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    StringBuilder invalidMessage = new StringBuilder();
    if(!violations.isEmpty()) {
      log.debug("Task has invalid data:\n");
      for (ConstraintViolation<Task> violation : violations) {
        log.debug("-" + violation.getMessage());
        invalidMessage.append(violation.getMessage() + "\n") ;
      }
      throw new InvalidDataException(invalidMessage.toString());
    }

    User user = userService.findByUsername(username);
    task.setUser(user);
    log.debug("Trying to add task: " + task + "for user: " + username);

    return taskRepository.save(task);
  }

  public Task updateTask(Task task) {
    log.debug("Trying to update task: " + task);

    // Validate id exists
    if(!taskRepository.existsById(task.getId())) {
      log.debug("Invalid Task Id: Task with id # " + task.getId()
        + "does not exist!");
      throw new NotFoundException("Task not found! Task with id # " + task.getId() + "does not exist!");
    }

    // Validate data inputs
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<Task>> violations = validator.validate(task);
    StringBuilder invalidMessage = new StringBuilder();
    if(!violations.isEmpty()) {
      log.debug("Task has invalid data:\n");
      for (ConstraintViolation<Task> violation : violations) {
        log.debug("-" + violation.getMessage());
        invalidMessage.append(violation.getMessage() + "\n");
      }
      throw new InvalidDataException(invalidMessage.toString());
    }

    Task updatedTask = findById(task.getId());
    updatedTask.setName(task.getName());
    updatedTask.setDescription(task.getDescription());
    updatedTask.setStart(task.getStart());
    updatedTask.setEnd(task.getEnd());
    updatedTask.setSeverity(task.getSeverity());

    return taskRepository.save(updatedTask);
  }

  public void deleteTask(Integer id) {
    log.debug("Trying to delete task with id: " + id);

    if(!taskRepository.existsById(id)){
      log.debug("Invalid Task Id: Task with id # " + id
        + "does not exist!");
      throw new NotFoundException("Task not found! Task with id # " + id + "does not exist!");
    }

    taskRepository.deleteById(id);
  }

}
