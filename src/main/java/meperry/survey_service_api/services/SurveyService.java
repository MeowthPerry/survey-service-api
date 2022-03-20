package meperry.survey_service_api.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import meperry.survey_service_api.models.Status;
import meperry.survey_service_api.models.Survey;
import meperry.survey_service_api.repositories.SurveyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyService {
  private final SurveyRepository surveyRepository;

  @Autowired
  public SurveyService(SurveyRepository surveyRepository) {
    this.surveyRepository = surveyRepository;
  }

  public Survey getSurveyById(Long id) {
    return surveyRepository.getById(id);
  }

  public void saveSurvey(Survey survey) {
    survey.setStatus(Status.ACTIVE);
    surveyRepository.save(survey);
  }

  public void updateSurvey(Survey survey) {
    surveyRepository.save(survey);
  }

  public void deleteSurveyById(Long id) {
    surveyRepository.deleteById(id);
  }

  public List<Survey> getCurrentSurveys() {
    return surveyRepository.getAllByStartDateBeforeAndExpirationDateAfter(
        Date.valueOf(LocalDate.now()), Date.valueOf(LocalDate.now()));
  }
}
