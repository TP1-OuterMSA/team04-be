package com.example.crawler.meal.component;

import com.example.kafka_schemas.NutritionEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
@Slf4j
@Component
public class KafkaNutritionEventListener {

  @KafkaListener(topics = "meal.nutrition.updated")
  public void consume(NutritionEvent event) {
    log.info("받은 NutritionEvent - 음식명: {}, 중량: {}, 칼로리: {}kcal, 단백질: {}, 지방: {}, 탄수화물: {}",
        event.getFoodName(),
        event.getFoodWeight(),
        event.getKcal(),
        event.getProtein(),
        event.getFat(),
        event.getCarb()
    );
  }
}
