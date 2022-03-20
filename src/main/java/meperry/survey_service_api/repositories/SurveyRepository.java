package meperry.survey_service_api.repositories;

import java.sql.Date;
import java.util.List;
import meperry.survey_service_api.models.Survey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends JpaRepository<Survey, Long> {

  List<Survey> getAllByStartDateBeforeAndExpirationDateAfter(Date date1, Date date2);
}
