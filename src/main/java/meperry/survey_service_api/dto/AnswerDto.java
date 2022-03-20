package meperry.survey_service_api.dto;

import java.util.List;
import lombok.Data;
import meperry.survey_service_api.models.AnswerUnit;

@Data
public class AnswerDto {
  private List<AnswerUnit> answerUnits;
  private Long questionId;
}
