package com.hcl.taskmanagerwebapp.security;

import com.hcl.taskmanagerwebapp.model.User;
import com.hcl.taskmanagerwebapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthenticatorUserDetailsService implements UserDetailsService {

  @Autowired
  private UserService userService;

  @Override
  public UserDetails loadUserByUsername(String username) {
    final User user = userService.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException(username);
    }
    return new AuthenticatorUserPrinciple(user);
  }

}
