package meperry.survey_service_api.controllers;

import java.util.HashMap;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import meperry.survey_service_api.dto.AuthenticationRequestDto;
import meperry.survey_service_api.dto.ReloginRequestDto;
import meperry.survey_service_api.models.User;
import meperry.survey_service_api.security.jwt.JwtTokenProvider;
import meperry.survey_service_api.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/auth")
public class AuthenticationController {
  private final AuthenticationManager authenticationManager;
  private final JwtTokenProvider jwtTokenProvider;
  private final UserService userService;

  @Autowired
  public AuthenticationController(
      AuthenticationManager authenticationManager,
      JwtTokenProvider jwtTokenProvider,
      UserService userService) {
    this.authenticationManager = authenticationManager;
    this.jwtTokenProvider = jwtTokenProvider;
    this.userService = userService;
  }

  @PostMapping("/login")
  public ResponseEntity<Map<Object, Object>> login(
      @RequestBody AuthenticationRequestDto requestDto) {
    try {
      String username = requestDto.getUsername();
      authenticationManager.authenticate(
          new UsernamePasswordAuthenticationToken(username, requestDto.getPassword()));
      User user = userService.findByUsername(username);
      if (user == null) {
        throw new UsernameNotFoundException("User with username: " + username + " not found");
      }
      String token = jwtTokenProvider.createToken(username, user.getRole());
      String refreshToken = jwtTokenProvider.createRefreshToken(username, user.getRole());
      Map<Object, Object> response = new HashMap<>();
      response.put("accessToken", token);
      response.put("refreshToken", refreshToken);
      return ResponseEntity.ok(response);
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid username or password");
    }
  }

  @PostMapping("/relogin")
  public ResponseEntity<Map<Object, Object>> relogin(@RequestBody ReloginRequestDto requestDto) {
    try {
      return ResponseEntity.ok(jwtTokenProvider.refreshAccessToken(requestDto.getRefreshToken()));
    } catch (AuthenticationException e) {
      throw new BadCredentialsException("Invalid access or refresh tokens");
    }
  }
}
