package meperry.survey_service_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import meperry.survey_service_api.models.User;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
  private Long id;
  private String username;
  private String email;
  private String password;

  public User toUser() {
    User user = new User();
    user.setId(id);
    user.setUsername(username);
    user.setEmail(email);
    user.setPassword(password);
    return user;
  }
}
