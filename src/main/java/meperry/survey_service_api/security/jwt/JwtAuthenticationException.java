package meperry.survey_service_api.security.jwt;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class JwtAuthenticationException extends AuthenticationException {

  public JwtAuthenticationException() {
    super("JWT token is expired or invalid");
  }
}
