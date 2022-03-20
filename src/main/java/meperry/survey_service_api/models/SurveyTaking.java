package meperry.survey_service_api.models;

import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "survey_takings")
@Data
public class SurveyTaking extends BaseEntity {
  @ManyToOne
  @JoinColumn(name = "survey_id", nullable = false)
  private Survey survey;

  @Column(name = "participant_id")
  private Long participantId;

  @OneToMany(mappedBy = "surveyTaking", cascade = CascadeType.ALL)
  private List<Answer> answers;
}
