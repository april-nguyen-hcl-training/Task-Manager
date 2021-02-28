package com.hcl.taskmanagerapi.controller;

import com.hcl.taskmanagerapi.model.Task;
import com.hcl.taskmanagerapi.model.User;
import com.hcl.taskmanagerapi.service.TaskService;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
public class TaskController {
  @Value("${allowedUrls}")
  private String[] allowedUrls;

  private static final Logger log = LogManager.getLogger(TaskController.class);

  @Autowired
  private TaskService taskService;

  @ApiOperation(value = "Search for tasks with a username", response = List.class)
  @GetMapping("/username/{username}")
  public List<Task> getByUser(@PathVariable String username) {
    return this.taskService.findByUsername(username);
  }

  @ApiOperation(value = "Search for a task with an ID", response = Task.class)
  @GetMapping("/{id}")
  public Task get(@PathVariable Integer id){
    return taskService.findById(id);
  }

  @ApiOperation(value = "Add a task", response = Task.class)
  @PostMapping("/{username}")
  public Task add(@RequestBody Task task, @PathVariable String username){
    return taskService.addTask(username, task);
  }

  @ApiOperation(value = "Delete a task")
  @DeleteMapping("/{id}")
  public ResponseEntity delete(@PathVariable Integer id) {
    log.debug("Request received to delete task with id: " + id);
    taskService.deleteTask(id);
    return new ResponseEntity("Task deleted successfully", HttpStatus.OK);
  }

  @ApiOperation(value = "Update a task", response = Task.class)
  @PutMapping
  public Task update(@RequestBody Task task) {
    return taskService.updateTask(task);
  }

}
