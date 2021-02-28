package com.hcl.taskmanagerapi.service;

import com.hcl.taskmanagerapi.exception.InvalidDataException;
import com.hcl.taskmanagerapi.exception.NotFoundException;
import com.hcl.taskmanagerapi.model.User;
import com.hcl.taskmanagerapi.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.validation.*;
import java.util.Set;

@Service
public class UserService {

  private static final Logger log = LogManager.getLogger(UserService.class);

  @Autowired
  private UserRepository userRepository;

  public User findByUsername(String username) {
    log.debug("Finding user with username: " + username);
    return this.userRepository.findByUsername(username);
  }

  public User findById(Integer id) {
    log.debug("Finding user with id: " + id);
    return this.userRepository.findById(id).orElseThrow(() -> new NotFoundException("User not found!"));
  }

  public User addUser(User user) {
    log.debug("Trying to add user: " + user);

    // validate username not taken
    if(usernameTaken(user.getUsername())) {
      throw new InvalidDataException("Username is taken!");
    }

    // Validate data inputs
    Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
    Set<ConstraintViolation<User>> violations = validator.validate(user);
    StringBuilder invalidMessage = new StringBuilder();
    if(!violations.isEmpty()) {
      log.debug("User has invalid data:\n");
      for (ConstraintViolation<User> violation : violations) {
        log.debug("-" + violation.getMessage());
        invalidMessage.append(violation.getMessage() + "\n");
      }
      throw new InvalidDataException(invalidMessage.toString());
    }

    return userRepository.save(user);
  }

  public Boolean usernameTaken(String username) {
    log.debug("Checking if username is taken: " + username);
    return this.userRepository.existsByUsername(username);
  }

}
