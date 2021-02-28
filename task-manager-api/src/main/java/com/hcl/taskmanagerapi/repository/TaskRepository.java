package com.hcl.taskmanagerapi.repository;

import com.hcl.taskmanagerapi.model.Task;
import com.hcl.taskmanagerapi.model.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface TaskRepository extends CrudRepository<Task, Integer> {
  List<Task> findByUser(User user);
}
