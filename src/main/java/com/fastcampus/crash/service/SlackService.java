package com.fastcampus.crash.service;

import com.fastcampus.crash.model.registration.Registration;
import com.fastcampus.crash.model.slack.SlackBlock;
import com.fastcampus.crash.model.slack.SlackBlockBasedMessage;
import com.fastcampus.crash.model.slack.SlackText;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class SlackService {
  private static final Logger logger = LoggerFactory.getLogger(SlackService.class);
  private static final RestClient restClient =
      RestClient.builder()
          .baseUrl(
              "https://hooks.slack.com/services/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")
          .build();

  private String getRegistrationPageLink(Registration registration) {
    var baseLink = "https://dev-jayce.github.io/crash/registration.html?";
    Long registrationId = registration.registrationId();
    String username = registration.user().username();
    Long sessionId = registration.session().sessionId();
    return baseLink + "registration=" + registrationId + "," + username + "," + sessionId;
  }

  public void sendRegistrationNotification(Registration registration) {
    var link = getRegistrationPageLink(registration);
    var slackBlockBasedMessage =
        new SlackBlockBasedMessage(
            List.of(
                new SlackBlock(
                    "section",
                    new SlackText(
                        "mrkdwn", ":collision: *CRASH* <" + link + "|Registration Details>"))));

    var response =
        restClient
            .post()
            .body(slackBlockBasedMessage)
            .retrieve()
            .onStatus(
                HttpStatusCode::is4xxClientError,
                ((request, response1) -> {
                  logger.error("CLIENT ERROR");
                }))
            .body(String.class);
    logger.info(response);
  }
}
