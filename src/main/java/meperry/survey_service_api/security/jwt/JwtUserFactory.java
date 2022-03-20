package meperry.survey_service_api.security.jwt;

import java.util.ArrayList;
import java.util.List;
import meperry.survey_service_api.models.Status;
import meperry.survey_service_api.models.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public final class JwtUserFactory {

  public static JwtUser create(User user) {
    List<GrantedAuthority> grantedAuthorityList = new ArrayList<>();
    grantedAuthorityList.add(new SimpleGrantedAuthority(user.getRole().name()));
    return new JwtUser(
        user.getId(),
        user.getUsername(),
        user.getEmail(),
        user.getPassword(),
        grantedAuthorityList,
        user.getStatus().equals(Status.ACTIVE));
  }
}
