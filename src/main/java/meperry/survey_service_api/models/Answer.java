package meperry.survey_service_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "answers")
@Data
public class Answer extends BaseEntity {
  @OneToMany(mappedBy = "answer", cascade = CascadeType.ALL)
  private List<AnswerUnit> answerUnits;

  @ManyToOne
  @JoinColumn(name = "question_id", nullable = false)
  private Question question;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "survey_taking_id", nullable = false)
  private SurveyTaking surveyTaking;
}
