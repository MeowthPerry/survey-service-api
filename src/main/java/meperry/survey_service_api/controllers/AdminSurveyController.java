package meperry.survey_service_api.controllers;

import lombok.extern.slf4j.Slf4j;
import meperry.survey_service_api.dto.SurveyDto;
import meperry.survey_service_api.models.Survey;
import meperry.survey_service_api.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/admin/survey")
public class AdminSurveyController {
  private final SurveyService surveyService;

  @Autowired
  public AdminSurveyController(SurveyService surveyService) {
    this.surveyService = surveyService;
  }

  @PostMapping
  public void saveSurvey(@RequestBody SurveyDto surveyDto) {
    surveyService.saveSurvey(surveyDto.toSurvey());
  }

  @PutMapping("/{id}")
  public void updateSurvey(@PathVariable Long id, @RequestBody SurveyDto surveyDto) {
    Survey survey = surveyService.getSurveyById(id);
    surveyDto.copyFields(survey);
    surveyService.updateSurvey(survey);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteSurvey(@PathVariable Long id) {
    surveyService.deleteSurveyById(id);
  }
}
