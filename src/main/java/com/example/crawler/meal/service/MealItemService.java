package com.example.crawler.meal.service;

import com.example.crawler.meal.component.MealItemFilter;
import com.example.crawler.meal.component.MenuKafkaProducer;
import com.example.crawler.meal.entity.MealItem;
import com.example.crawler.meal.repository.MealItemRepository;
import com.example.crawler.meal.vo.MealItemId;
import com.example.crawler.meal.vo.MealItemList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MealItemService {

  private final MealItemRepository mealItemRepository;
  private final MealTableCrawlerService mealTableCrawlerService;
  private final MealItemFilter mealItemFilter;
  private final MenuKafkaProducer menuKafkaProducer;

  public List<MealItem> getMealItems() {
    List<MealItem> crawledItems = mealTableCrawlerService.mealTableCrawler();

    MealItemList itemList = new MealItemList(crawledItems);
    List<MealItemId> existingIds = mealItemRepository.findAllIds()
            .stream()
            .map(MealItemId::new)
            .toList();

    List<MealItem> newItems = mealItemFilter.filterNewItems(itemList, existingIds);

    if (newItems.isEmpty()) {
      return crawledItems;
    }

    mealItemRepository.saveAll(newItems);
    menuKafkaProducer.sendAll(newItems);
    return crawledItems;
  }
}
