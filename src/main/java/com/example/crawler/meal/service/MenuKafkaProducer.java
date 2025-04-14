package com.example.crawler.meal.service;

import com.example.crawler.meal.entity.MealItem;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MenuKafkaProducer {
    private final KafkaTemplate<String, String> kafkaTemplate;

    public void sendAllMealItems(List<MealItem> items) {
        List<Map<String, Object>> simplifiedList = items.stream().map(MealItem::toSimpleJson).toList();

        try {
            String payload = new ObjectMapper().writeValueAsString(simplifiedList);
            String topic = "meal.web-crawler.updated";
            kafkaTemplate.send(topic, "meal-data", payload);

            log.info("Kafka 전송 성공 - payload: {}", payload);
        } catch (JsonProcessingException e) {
            log.error("Kafka JSON 직렬화 실패", e);
        }
    }
}