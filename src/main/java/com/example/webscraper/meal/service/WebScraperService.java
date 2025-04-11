package com.example.webscraper.meal.service;

import com.example.webscraper.meal.repository.MealItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import webscraper.meal.model.MenuItem;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import webscraper.meal.service.MenuKafkaProducer;
import webscraper.meal.service.MenuPersist;
import webscraper.meal.service.MenuScraper;

@Slf4j
@Service
@RequiredArgsConstructor
public class WebScraperService {
    private final MenuScraper menuScraper;
    private final MenuPersist menuPersister;
    private final MenuKafkaProducer menuKafkaProducer;

    public MealItemRepository getMeal() {
        Map<String, Object> result = new HashMap<>();
        List<MenuItem> menuList = menuScraper.scrap();
        result.put("mealMenu", menuList);

        for (MenuItem item : menuList) {
            try {
                menuPersister.save(item);
            } catch (Exception e) {
                log.error("DB 저장 실패 - {}: {}", item.generateId(), e.getMessage());
            }

            try {
                menuKafkaProducer.sendMenu(item);
            } catch (Exception e) {
                log.error("Kafka 전송 실패 - {}: {}", item.generateId(), e.getMessage());
            }
        }

        return result;
    }

    @Scheduled(fixedRate = 3600000)
    public void scheduledMeal() {
        log.info("식단 크롤링 스케줄 실행");
        getMeal();
    }
}
