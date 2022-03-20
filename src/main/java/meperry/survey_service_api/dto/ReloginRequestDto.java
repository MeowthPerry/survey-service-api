package meperry.survey_service_api.dto;

import lombok.Data;

@Data
public class ReloginRequestDto {
  private String refreshToken;
}
