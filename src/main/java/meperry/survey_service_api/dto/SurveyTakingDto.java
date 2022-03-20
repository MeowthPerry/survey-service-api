package meperry.survey_service_api.dto;

import java.util.List;
import lombok.Data;

@Data
public class SurveyTakingDto {
  private List<AnswerDto> answers;
}
