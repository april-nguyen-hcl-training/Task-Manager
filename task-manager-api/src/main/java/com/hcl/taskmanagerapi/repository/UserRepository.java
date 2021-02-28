package com.hcl.taskmanagerapi.repository;

import com.hcl.taskmanagerapi.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
  boolean existsByUsername(String username);
  User findByUsername(String username);
}
