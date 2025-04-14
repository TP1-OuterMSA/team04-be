package com.example.crawler.meal.service;

import com.example.crawler.meal.entity.MealItem;
import com.example.crawler.meal.repository.MealItemRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MealItemService {

  private final MenuKafkaProducer menuKafkaProducer;
  private final MealItemCrawlerService mealItemCrawlerService;
  private final MealItemRepository mealItemRepository;

  // TODO: 리팩토링
  public List<MealItem> getMealItems() {
    List<MealItem> menuList = mealItemCrawlerService.mealItemCrawler();
    if (menuList.isEmpty()) {
      throw new IllegalStateException("식단 데이터를 수집하지 못했습니다.");
    }

    List<String> existingIds = mealItemRepository.findAll().stream()
        .map(MealItem::generateId)
        .toList();

    List<MealItem> newItems = menuList.stream()
        .filter(item -> !existingIds.contains(item.generateId()))
        .toList();

    if (!newItems.isEmpty()) {
      mealItemRepository.saveAll(newItems);
//      menuKafkaProducer.sendAllMealItems(newItems);
    }

    return mealItemRepository.findAll();
  }


  @Scheduled(fixedRate = 3600000)
  public void scheduledMeal() {
    log.info("식단 크롤링 스케줄 실행");
    getMealItems();
  }
}
