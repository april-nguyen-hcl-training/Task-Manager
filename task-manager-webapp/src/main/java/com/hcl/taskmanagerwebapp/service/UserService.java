package com.hcl.taskmanagerwebapp.service;

import com.hcl.taskmanagerwebapp.model.User;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class UserService {

  private static final Logger log = LogManager.getLogger(UserService.class);

  @Value("${apiUrl.user.findByUsername}")
  private String findUrl;
  @Value("${apiUrl.user.add}")
  private String addUrl;
  @Value("${apiUrl.user.usernameTaken}")
  private String usernameTakenUrl;

  @Autowired
  private PasswordEncoder passwordEncoder;

  @Autowired
  private RestTemplate restTemplate;

  public User findByUsername(String username) {
    String url = findUrl+username;
    log.debug("Sending get request to " + url + " to find user with username: " + username);

    User user = restTemplate.getForObject(url, User.class);
    return user;
  }
  
  public User addUser(User account) {
    User user = new User();
    user.setUsername(account.getUsername());
    user.setPassword(passwordEncoder.encode(account.getPassword()));
    user.setFirstName(account.getFirstName());
    user.setLastName(account.getLastName());
    user.setEmail(account.getEmail());

    log.debug("Sending post request to " + addUrl + " to add user: " + user);

    return restTemplate.postForObject(addUrl, user, User.class);
  }

  public Boolean usernameExists(String username) {
    log.debug("Sending get request to " + usernameTakenUrl + username + " to check if username exists: " + username);
    return restTemplate.getForObject(usernameTakenUrl + username, Boolean.class);
  }
}
