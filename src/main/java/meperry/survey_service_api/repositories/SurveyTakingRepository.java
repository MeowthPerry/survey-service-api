package meperry.survey_service_api.repositories;

import java.util.List;
import meperry.survey_service_api.models.SurveyTaking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyTakingRepository extends JpaRepository<SurveyTaking, Long> {
  List<SurveyTaking> getAllByParticipantId(Long participantId);
}
