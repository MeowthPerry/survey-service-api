package meperry.survey_service_api.repositories;

import meperry.survey_service_api.models.AnswerOption;
import meperry.survey_service_api.models.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AnswerOptionRepository extends JpaRepository<AnswerOption, Long> {

  void deleteAllByQuestion(Question question);
}
