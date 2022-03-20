package meperry.survey_service_api.dto;

import lombok.Data;

@Data
public class AuthenticationRequestDto {
  private String username;
  private String password;
}
