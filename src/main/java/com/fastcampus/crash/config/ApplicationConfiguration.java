package com.fastcampus.crash.config;

import com.fastcampus.crash.model.sessionspeaker.SessionSpeaker;
import com.fastcampus.crash.model.sessionspeaker.SessionSpeakerPostRequestBody;
import com.fastcampus.crash.model.user.UserSignUpRequestBody;
import com.fastcampus.crash.service.SessionSpeakerService;
import com.fastcampus.crash.service.UserService;
import java.util.stream.IntStream;
import net.datafaker.Faker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfiguration {

  private static final Faker faker = new Faker();

  @Autowired private UserService userService;

  @Autowired private SessionSpeakerService sessionSpeakerService;

  @Bean
  public ApplicationRunner applicationRunner() {
    return new ApplicationRunner() {
      @Override
      public void run(ApplicationArguments args) throws Exception {
        createTestUsers();
        createTestSessionSpeakers(10);
      }
    };
  }

  private void createTestUsers() {
    userService.signUp(new UserSignUpRequestBody("jayce", "1234", "Dev Jayce", "jayce@crash.com"));
    userService.signUp(new UserSignUpRequestBody("jay", "1234", "Dev Jay", "jay@crash.com"));
    userService.signUp(new UserSignUpRequestBody("rose", "1234", "Dev Rose", "rose@crash.com"));
    userService.signUp(new UserSignUpRequestBody("rosa", "1234", "Dev Rosa", "rosa@crash.com"));
  }

  private void createTestSessionSpeakers(int numberOfSpeakers) {
    var sessionSpeakers =
        IntStream.range(0, numberOfSpeakers).mapToObj(i -> createTestSessionSpeaker()).toList();
  }

  private SessionSpeaker createTestSessionSpeaker() {
    var name = faker.name().fullName();
    var company = faker.company().name();
    var description = faker.shakespeare().romeoAndJulietQuote();

    return sessionSpeakerService.createSessionSpeaker(
        new SessionSpeakerPostRequestBody(company, name, description));
  }
}
