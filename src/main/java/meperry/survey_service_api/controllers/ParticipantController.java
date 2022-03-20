package meperry.survey_service_api.controllers;

import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import meperry.survey_service_api.dto.SurveyTakingDto;
import meperry.survey_service_api.models.Answer;
import meperry.survey_service_api.models.Survey;
import meperry.survey_service_api.models.SurveyTaking;
import meperry.survey_service_api.services.QuestionService;
import meperry.survey_service_api.services.SurveyService;
import meperry.survey_service_api.services.SurveyTakingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/participant")
public class ParticipantController {
  private final SurveyService surveyService;
  private final SurveyTakingService surveyTakingService;
  private final QuestionService questionService;

  @Autowired
  public ParticipantController(
      SurveyService surveyService,
      SurveyTakingService surveyTakingService,
      QuestionService questionService) {
    this.surveyService = surveyService;
    this.surveyTakingService = surveyTakingService;
    this.questionService = questionService;
  }

  @GetMapping(value = "/survey")
  public ResponseEntity<List<Survey>> getCurrentSurveys() {
    return ResponseEntity.ok(surveyService.getCurrentSurveys());
  }

  @GetMapping(value = "/surveyTaking/{participantId}")
  public ResponseEntity<List<SurveyTaking>> getCompletedSurveys(@PathVariable Long participantId) {
    return ResponseEntity.ok(surveyTakingService.getAllByParticipantId(participantId));
  }

  @PostMapping("/surveyTaking/{participantId}/survey/{surveyId}")
  public void saveSurveyTaking(
      @PathVariable Long participantId,
      @PathVariable Long surveyId,
      @RequestBody SurveyTakingDto surveyTakingDto) {
    SurveyTaking surveyTaking = new SurveyTaking();
    surveyTaking.setParticipantId(participantId);
    surveyTaking.setSurvey(surveyService.getSurveyById(surveyId));
    surveyTaking.setAnswers(
        surveyTakingDto.getAnswers().stream()
            .map(
                answerDto -> {
                  Answer answer = new Answer();
                  answer.setAnswerUnits(answerDto.getAnswerUnits());
                  answer.getAnswerUnits().forEach(answerUnit -> answerUnit.setAnswer(answer));
                  answer.setQuestion(questionService.getById(answerDto.getQuestionId()));
                  answer.setSurveyTaking(surveyTaking);
                  return answer;
                })
            .collect(Collectors.toList()));
    surveyTakingService.saveSurveyTaking(surveyTaking);
  }
}
