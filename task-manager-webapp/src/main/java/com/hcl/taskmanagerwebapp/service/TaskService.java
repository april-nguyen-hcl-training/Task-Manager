package com.hcl.taskmanagerwebapp.service;

import com.hcl.taskmanagerwebapp.model.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class TaskService {

  private static final Logger log = LogManager.getLogger(TaskService.class);

  @Value("${apiUrl.task.findByUsername}")
  private String findByUsernameUrl;

  @Value("${apiUrl.task.findById}")
  private String findByIdUrl;

  @Value("${apiUrl.task.add}")
  private String addUrl;

  @Value("${apiUrl.task.update}")
  private String updateUrl;

  @Value("${apiUrl.task.delete}")
  private String deleteUrl;

  @Autowired
  private RestTemplate restTemplate;

  public List<Task> findByUsername(String username) {
    String url = findByUsernameUrl+username;
    log.debug("Sending get request to " + url + " to find tasks of username: " + username);

    List<Task> tasks = restTemplate.getForObject(url, List.class);
    return tasks;
  }

  public Task findById(Integer id) {
    String url = findByIdUrl+id;
    log.debug("Sending get request to " + url + " to find task with id: " + id);

    return restTemplate.getForObject(url, Task.class);
  }

  public Task add(String username, Task task) {

    log.debug("Sending post request to " + addUrl+username + " to add task: " + task.toString());

    return restTemplate.postForObject(addUrl+username, task, Task.class);
  }

  public void update(Task task) {

    log.debug("Sending put request to " + updateUrl + " to update task: " + task.toString());

    restTemplate.put(updateUrl, task);
  }

  public void delete(Integer id) {
    String url = deleteUrl+id;
    log.debug("Sending delete request to " + deleteUrl + " to delete task with id: " + id);

    restTemplate.delete(url);
  }

}
