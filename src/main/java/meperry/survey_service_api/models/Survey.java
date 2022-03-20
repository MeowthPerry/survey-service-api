package meperry.survey_service_api.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.sql.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;

@Entity
@Table(name = "surveys")
@Data
public class Survey extends BaseEntity {
  @Column(name = "name", unique = true)
  private String name;

  @Column(name = "start_date", updatable = false)
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date startDate;

  @Column(name = "expiration_date")
  @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd")
  private Date expirationDate;

  @Column(name = "description")
  private String description;

  @OneToMany(mappedBy = "survey")
  private List<Question> questions;
}
