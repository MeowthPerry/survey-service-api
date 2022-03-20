package meperry.survey_service_api.services;

import meperry.survey_service_api.models.Question;
import meperry.survey_service_api.repositories.AnswerOptionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AnswerOptionService {
  private final AnswerOptionRepository answerOptionRepository;

  @Autowired
  public AnswerOptionService(AnswerOptionRepository answerOptionRepository) {
    this.answerOptionRepository = answerOptionRepository;
  }

  public void deleteAllByQuestion(Question question) {
    answerOptionRepository.deleteAllByQuestion(question);
  }
}
