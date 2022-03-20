package meperry.survey_service_api.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "answer_units")
@Data
public class AnswerUnit extends BaseEntity {
  @Column(name = "content")
  private String content;

  @JsonIgnore
  @ManyToOne
  @JoinColumn(name = "answer_id", nullable = false)
  private Answer answer;
}
