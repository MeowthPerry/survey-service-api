package meperry.survey_service_api.security.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import meperry.survey_service_api.models.Role;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class JwtTokenProvider {

  private final UserDetailsService userDetailsService;

  @Value("${jwt.token.secret}")
  private String secret;

  @Value("${jwt.token.accessExpirationMs}")
  private long accessExpirationMs;

  @Value("${jwt.token.refreshExpirationMs}")
  private long refreshExpirationMs;

  private final List<String> tokensBlacklist = new ArrayList<>();

  public JwtTokenProvider(UserDetailsService userDetailsService) {
    this.userDetailsService = userDetailsService;
  }

  @PostConstruct
  protected void init() {
    secret = Base64.getEncoder().encodeToString(secret.getBytes());
  }

  public String createToken(String username, Role role) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("type", TokenType.ACCESS.toString());
    claims.put("role", role);
    Date now = new Date();
    Date validity = new Date(now.getTime() + accessExpirationMs);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public String createRefreshToken(String username, Role role) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("type", TokenType.REFRESH.toString());
    claims.put("role", role);
    Date now = new Date();
    Date validity = new Date(now.getTime() + refreshExpirationMs);
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(validity)
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  private String createRefreshToken(String username, Role role, Date expiration) {
    Claims claims = Jwts.claims().setSubject(username);
    claims.put("type", TokenType.REFRESH.toString());
    claims.put("role", role);
    Date now = new Date();
    return Jwts.builder()
        .setClaims(claims)
        .setIssuedAt(now)
        .setExpiration(expiration)
        .signWith(SignatureAlgorithm.HS256, secret)
        .compact();
  }

  public Authentication getAuthentication(String token) {
    log.info("IN getAuthentication");
    UserDetails userDetails = this.userDetailsService.loadUserByUsername(getUsername(token));
    return new UsernamePasswordAuthenticationToken(userDetails, "", userDetails.getAuthorities());
  }

  private String getUsername(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getSubject();
  }

  private Date getExpiration(String token) {
    return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody().getExpiration();
  }

  private Role getRole(String token) {
    return Role.valueOf(
        Jwts.parser()
            .setSigningKey(secret)
            .parseClaimsJws(token)
            .getBody()
            .get("role", String.class));
  }

  public String resolveToken(HttpServletRequest req) {
    String bearerToken = req.getHeader("Authorization");
    if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
      return bearerToken.substring(7);
    }
    return null;
  }

  public Map<Object, Object> refreshAccessToken(String refreshToken) {
    if (validateRefreshToken(refreshToken)) {
      tokensBlacklist.add(refreshToken);
      String username = getUsername(refreshToken);
      Role role = getRole(refreshToken);
      Date expiration = getExpiration(refreshToken);
      String newAccessToken = createToken(username, role);
      String newRefreshToken = createRefreshToken(username, role, expiration);
      Map<Object, Object> response = new HashMap<>();
      response.put("accessToken", newAccessToken);
      response.put("refreshToken", newRefreshToken);
      return response;
    } else {
      throw new JwtAuthenticationException();
    }
  }

  public boolean validateAccessToken(String token) {
    try {
      if (tokensBlacklist.contains(token)) {
        throw new JwtAuthenticationException();
      }
      Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return !tokensBlacklist.contains(token)
          && !claims.getBody().getExpiration().before(new Date())
          && Objects.equals(claims.getBody().get("type", String.class), TokenType.ACCESS.toString());
    } catch (JwtException | IllegalArgumentException e) {
      throw new JwtAuthenticationException();
    }
  }

  private boolean validateRefreshToken(String token) {
    try {
      if (tokensBlacklist.contains(token)) {
        throw new JwtAuthenticationException();
      }
      Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token);
      return !tokensBlacklist.contains(token)
          && !claims.getBody().getExpiration().before(new Date())
          && Objects.equals(claims.getBody().get("type", String.class), TokenType.REFRESH.toString());
    } catch (JwtException | IllegalArgumentException e) {
      e.printStackTrace();
      throw new JwtAuthenticationException();
    }
  }
}
