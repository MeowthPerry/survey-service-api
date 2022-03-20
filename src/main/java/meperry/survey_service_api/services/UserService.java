package meperry.survey_service_api.services;

import lombok.extern.slf4j.Slf4j;
import meperry.survey_service_api.models.User;
import meperry.survey_service_api.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class UserService {
  private final UserRepository userRepository;

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public User findByUsername(String username) {
    User result = userRepository.findByUsername(username);
    return result;
  }
}
