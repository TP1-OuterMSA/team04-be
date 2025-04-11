package webscraper.meal.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import webscraper.meal.model.MenuItem;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String topic = "meal.web-scraper.updated";

    public void sendMenu(MenuItem item) {
        String key = item.generateId();
        String payload = item.toMealJson();


//        kafkaTemplate.send(topic, key, payload);
//        log.info("Kafka 전송 성공 - key: {}, payload: {}", key, payload);
    }
}
