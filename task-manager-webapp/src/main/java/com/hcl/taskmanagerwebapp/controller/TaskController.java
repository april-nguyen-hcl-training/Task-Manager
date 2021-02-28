package com.hcl.taskmanagerwebapp.controller;

import com.hcl.taskmanagerwebapp.model.Task;
import com.hcl.taskmanagerwebapp.service.TaskService;
import com.hcl.taskmanagerwebapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.List;

@Controller
public class TaskController {

  private static final Logger log = LogManager.getLogger(TaskController.class);

  @Autowired
  private TaskService taskService;
  @Autowired
  private UserService userService;

  @GetMapping("/tasks")
  public String showTasks(Model model, HttpServletRequest httpServletRequest) {
    String username = httpServletRequest.getRemoteUser();
    model.addAttribute("user", userService.findByUsername(username));

    model.addAttribute("taskList", taskService.findByUsername(username));
    return "jsp/tasks";
  }

  @GetMapping("/task")
  public String addTaskForm(Model model, HttpServletRequest httpServletRequest) {
    Task newTask = new Task();
    newTask.setStart(new Date());
    newTask.setEnd(new Date());

    model.addAttribute("taskInput", newTask);
    model.addAttribute("severityList", Task.Severity.values());
    return "jsp/addform";
  }

  @PostMapping("/task")
  public String addTask(@Valid @ModelAttribute("taskInput") Task taskInput, BindingResult bindingResult,
                        Model model, RedirectAttributes attributes, HttpServletRequest httpServletRequest) {
    log.debug("Got form input to add new task: " + taskInput.toString());

    boolean hasError = false;
    if (bindingResult.hasFieldErrors("name")) {
      String nameError = bindingResult.getFieldError("name").getDefaultMessage();
      log.debug("Invalid Task Input: " + nameError);
      model.addAttribute("nameAlert", nameError);
      hasError = true;
    }

    if(taskInput.getStart().after(taskInput.getEnd())) {
      log.debug("Invalid Task Input: Start Date should be before End Date");
      model.addAttribute("endAlert", "End Date should be the same as or after Start Date!");
      model.addAttribute("startAlert", "Start Date should be the same as  or before End Date!");
      hasError = true;
    }

    if (hasError) {
      model.addAttribute("taskInput", taskInput);
      model.addAttribute("severityList", Task.Severity.values());
      return "jsp/addform";
    }

    String username = httpServletRequest.getRemoteUser();

    taskService.add(username, taskInput);

    List<Task> tasks = taskService.findByUsername(username);
    attributes.addFlashAttribute("user", userService.findByUsername(username));
    attributes.addFlashAttribute("taskList", tasks);
    attributes.addFlashAttribute("successAlert", "Task Added Successfully!");
    return "redirect:/tasks";
  }

  @PostMapping("/task/delete/{id}")
  public String deleteTask(@PathVariable Integer id, HttpServletRequest httpServletRequest, RedirectAttributes attributes) {
    log.debug("Got request to delete task with id: " + id);

    taskService.delete(id);

    String username = httpServletRequest.getRemoteUser();
    List<Task> tasks = taskService.findByUsername(username);
    attributes.addFlashAttribute("user", userService.findByUsername(username));
    attributes.addFlashAttribute("taskList", tasks);
    attributes.addFlashAttribute("successAlert", "Task Deleted Successfully!");
    return "redirect:/tasks";
  }


  @GetMapping("/task/{id}")
  public String editTaskForm(@PathVariable Integer id, Model model) {
    model.addAttribute("taskInput", taskService.findById(id));
    model.addAttribute("severityList", Task.Severity.values());
    return "jsp/updateform";
  }

  @PostMapping("/task/update/{id}")
  public String updateTask(@PathVariable Integer id, @Valid @ModelAttribute("taskInput") Task taskInput, BindingResult bindingResult, Model model, RedirectAttributes attributes, HttpServletRequest httpServletRequest) {

    log.debug("Got form input to update task # " + taskInput.getId() + ": " + taskInput.toString());

    boolean hasError = false;
    if (bindingResult.hasFieldErrors("name")) {
      String nameError = bindingResult.getFieldError("name").getDefaultMessage();
      log.debug("Invalid Task Input: " + nameError);
      model.addAttribute("nameAlert", nameError);
      hasError = true;
    }

    if(taskInput.getStart().after(taskInput.getEnd())) {
      log.debug("Invalid Task Input: Start Date should be before End Date");
      model.addAttribute("endAlert", "End Date should be the same as or after Start Date!");
      model.addAttribute("startAlert", "Start Date should be the same as  or before End Date!");
      hasError = true;
    }

    if (hasError) {
      model.addAttribute("taskInput", taskInput);
      model.addAttribute("severityList", Task.Severity.values());
      return "jsp/updateform";
    }

    String username = httpServletRequest.getRemoteUser();

    taskService.update(taskInput);

    List<Task> tasks = taskService.findByUsername(username);
    attributes.addFlashAttribute("user", userService.findByUsername(username));
    attributes.addFlashAttribute("taskList", tasks);
    attributes.addFlashAttribute("successAlert", "Task Updated Successfully!");
    return "redirect:/tasks";
  }

}
