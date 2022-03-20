package meperry.survey_service_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "questions")
@Data
public class Question extends BaseEntity {
  @Column(name = "content")
  private String content;

  @Column(name = "question_type")
  @Enumerated(EnumType.STRING)
  private QuestionType questionType;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "survey_id", nullable = false)
  private Survey survey;

  @OneToMany(mappedBy = "question", cascade = CascadeType.ALL)
  private List<AnswerOption> answerOptions;
}
