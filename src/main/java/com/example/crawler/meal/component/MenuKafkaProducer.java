package com.example.crawler.meal.component;

import com.example.crawler.meal.entity.MealItem;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuKafkaProducer {

  private static final String TOPIC = "meal.web.crawler.updated";
  private static final String KEY = "meal.data";

  private final KafkaTemplate<String, MealEvent> kafkaTemplate;

  public void sendAll(List<MealItem> mealItems) {
    for (MealItem mealItem : mealItems) {
      send(mealItem);
    }
  }

  private void send(MealItem mealItem) {
    MealEvent mealEvent = mapToEvent(mealItem);
    ProducerRecord<String, MealEvent> record = new ProducerRecord<>(TOPIC, KEY, mealEvent);

    try {
      kafkaTemplate.send(record);
      log.info("Kafka 전송 성공 - {}", mealEvent);
    } catch (Exception exception) {
      log.error("Kafka 전송 실패 - {}", mealEvent, exception);
    }
  }

  private MealEvent mapToEvent(MealItem item) {
    return new MealEvent(
            item.mealType(),
            item.menuContent(),
            item.day().toString()
    );
  }
}
