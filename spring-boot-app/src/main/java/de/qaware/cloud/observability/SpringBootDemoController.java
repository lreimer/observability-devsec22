package de.qaware.cloud.observability;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.Instant;

@RestController
public class SpringBootDemoController {

    private static final Duration MAX_SLEEP_TIME = Duration.ofMillis(500);

    private static final Logger LOGGER = LoggerFactory.getLogger(SpringBootDemoController.class);

    @Value("${pong.url}")
    private String pongUrl;

    @GetMapping("/ping")
    public String ping() {
        LOGGER.info("Processing Ping");
        var sleepDuration = sleepForRandomTime();
        
        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity<String> response = restTemplate.getForEntity(pongUrl, String.class);
        return "Ping " + sleepDuration + " " + response.getBody();
    }

    @GetMapping("/pong")
    public String pong() {
        LOGGER.info("Processing Pong");
        var sleepDuration = sleepForRandomTime();
        return "Pong " + sleepDuration;
    }

    private Duration sleepForRandomTime() {
        try {
            var minSleepDuration = Duration.ofMillis(Math.round(MAX_SLEEP_TIME.toMillis() * Math.random()));
            LOGGER.info("Sleeping for at least {}", minSleepDuration);
            var start = Instant.now();
            Thread.sleep(minSleepDuration.toMillis());
            Duration sleptFor = Duration.between(start, Instant.now());
            LOGGER.info("Slept for {}", sleptFor);
            return sleptFor;
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new IllegalStateException("Interrupted during sleep", e);
        }
    }
}
