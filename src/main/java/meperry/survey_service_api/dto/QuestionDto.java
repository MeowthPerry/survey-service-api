package meperry.survey_service_api.dto;

import java.util.List;
import lombok.Data;
import meperry.survey_service_api.models.AnswerOption;
import meperry.survey_service_api.models.Question;
import meperry.survey_service_api.models.QuestionType;

@Data
public class QuestionDto {
  private Long id;
  private String content;
  private QuestionType questionType;
  private List<AnswerOption> answerOptions;

  public Question toQuestion() {
    Question question = new Question();
    question.setId(id);
    question.setContent(content);
    question.setQuestionType(questionType);
    question.setAnswerOptions(answerOptions);
    return question;
  }

  public void copyFields(Question question) {
    if (content != null) question.setContent(content);
    if (questionType != null) question.setQuestionType(questionType);
    if (answerOptions != null) question.setAnswerOptions(answerOptions);
  }
}
