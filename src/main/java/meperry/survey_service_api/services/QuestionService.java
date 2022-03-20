package meperry.survey_service_api.services;

import meperry.survey_service_api.models.Question;
import meperry.survey_service_api.models.Status;
import meperry.survey_service_api.repositories.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class QuestionService {
  private final QuestionRepository questionRepository;

  @Autowired
  public QuestionService(QuestionRepository questionRepository) {
    this.questionRepository = questionRepository;
  }

  public Question getById(Long id) {
    return questionRepository.getById(id);
  }

  public void saveQuestion(Question question) {
    question.setStatus(Status.ACTIVE);
    questionRepository.save(question);
  }

  public void updateQuestion(Question question) {
    questionRepository.save(question);
  }

  public void deleteQuestionById(Long id) {
    questionRepository.deleteById(id);
  }
}
