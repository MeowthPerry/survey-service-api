package meperry.survey_service_api.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.sql.Date;
import lombok.Data;
import meperry.survey_service_api.models.Survey;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class SurveyDto {
  private Long id;
  private String name;
  private Date startDate;
  private Date expirationDate;
  private String description;

  public Survey toSurvey() {
    Survey survey = new Survey();
    survey.setId(id);
    survey.setName(name);
    survey.setStartDate(startDate);
    survey.setExpirationDate(expirationDate);
    survey.setDescription(description);
    return survey;
  }

  public void copyFields(Survey survey) {
    if (name != null)
      survey.setName(name);
    if (expirationDate != null)
      survey.setExpirationDate(expirationDate);
    if (description != null)
      survey.setDescription(description);
  }
}
