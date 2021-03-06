package meperry.survey_service_api.security;

import lombok.extern.slf4j.Slf4j;
import meperry.survey_service_api.models.User;
import meperry.survey_service_api.security.jwt.JwtUserFactory;
import meperry.survey_service_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class JwtUserDetailsService implements UserDetailsService {

  private final UserService userService;

  @Autowired
  public JwtUserDetailsService(UserService userService) {
    this.userService = userService;
  }

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    User user = userService.findByUsername(username);
    if (user == null) {
      throw new UsernameNotFoundException("User with username: " + username + " not found");
    }
    return JwtUserFactory.create(user);
  }
}
