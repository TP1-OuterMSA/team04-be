package com.example.crawler.meal.component;

import com.example.crawler.meal.entity.FoodMenu;
import com.example.crawler.meal.entity.Menu;
import com.example.crawler.meal.repository.FoodMenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuKafkaProducer {

  private static final String TOPIC = "meal.web.crawler.updated";
  private static final String KEY = "meal.data";

  private final KafkaTemplate<String, MealEvent> kafkaTemplate;
  private final FoodMenuRepository foodMenuRepository;

  public void send(Menu menu) {
    List<FoodMenu> foodMenus = foodMenuRepository.findAll().stream()
            .filter(fm -> fm.getMenu().getId().equals(menu.getId()))
            .collect(Collectors.toList());

    String menuContent = foodMenus.stream()
            .map(fm -> fm.getFood().getName())
            .collect(Collectors.joining(" "));

    MealEvent event = new MealEvent(
            menu.getMealType(),
            menuContent,
            menu.getDate().toString()
    );

    ProducerRecord<String, MealEvent> record = new ProducerRecord<>(TOPIC, KEY, event);

    try {
      kafkaTemplate.send(record);
      log.info("Kafka 전송 성공 - {}", event);
    } catch (Exception e) {
      log.error("Kafka 전송 실패 - {}", event, e);
    }
  }
}
