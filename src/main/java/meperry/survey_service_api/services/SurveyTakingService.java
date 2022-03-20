package meperry.survey_service_api.services;

import java.util.List;
import meperry.survey_service_api.models.SurveyTaking;
import meperry.survey_service_api.repositories.SurveyTakingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SurveyTakingService {
  private final SurveyTakingRepository surveyTakingRepository;

  @Autowired
  public SurveyTakingService(
      SurveyTakingRepository surveyTakingRepository) {
    this.surveyTakingRepository = surveyTakingRepository;
  }

  public List<SurveyTaking> getAllByParticipantId(Long participantId) {
    return surveyTakingRepository.getAllByParticipantId(participantId);
  }

  public void saveSurveyTaking(SurveyTaking surveyTaking) {
    surveyTakingRepository.save(surveyTaking);
  }
}
