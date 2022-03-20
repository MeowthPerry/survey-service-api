package meperry.survey_service_api.controllers;

import lombok.extern.slf4j.Slf4j;
import meperry.survey_service_api.dto.QuestionDto;
import meperry.survey_service_api.models.Question;
import meperry.survey_service_api.services.AnswerOptionService;
import meperry.survey_service_api.services.QuestionService;
import meperry.survey_service_api.services.SurveyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping(value = "/admin/survey/{surveyId}/question")
public class AdminQuestionController {
  private final SurveyService surveyService;
  private final QuestionService questionService;
  private final AnswerOptionService answerOptionService;

  @Autowired
  public AdminQuestionController(SurveyService surveyService, QuestionService questionService,
      AnswerOptionService answerOptionService) {
    this.surveyService = surveyService;
    this.questionService = questionService;
    this.answerOptionService = answerOptionService;
  }

  @PostMapping
  public void saveQuestion(@PathVariable Long surveyId, @RequestBody QuestionDto questionDto) {
    Question question = questionDto.toQuestion();
    question.setSurvey(surveyService.getSurveyById(surveyId));
    if (question.getAnswerOptions() != null)
      question.getAnswerOptions().forEach(answer -> answer.setQuestion(question));
    questionService.saveQuestion(question);
  }

  @Transactional
  @PutMapping("/{id}")
  public void updateQuestion(@PathVariable Long id, @RequestBody QuestionDto questionDto) {
    Question question = questionService.getById(id);
    answerOptionService.deleteAllByQuestion(question);
    questionDto.copyFields(question);
    question.getAnswerOptions().forEach(answerOption -> answerOption.setQuestion(question));
    questionService.updateQuestion(question);
  }

  @DeleteMapping(value = "/{id}")
  public void deleteQuestion(@PathVariable Long id) {
    questionService.deleteQuestionById(id);
  }
}
