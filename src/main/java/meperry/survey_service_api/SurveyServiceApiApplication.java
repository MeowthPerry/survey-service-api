package meperry.survey_service_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SurveyServiceApiApplication {

  public static void main(String[] args) {
    SpringApplication.run(SurveyServiceApiApplication.class, args);
  }
}
