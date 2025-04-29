package com.example.crawler.meal.service;

import com.example.crawler.meal.component.MenuKafkaProducer;
import com.example.crawler.meal.component.NewMenuProcessor;
import com.example.crawler.meal.entity.Menu;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class MenuService {

  private final MenuKafkaProducer menuKafkaProducer;
  private final MealMenuCrawlerService mealMenuCrawlerService;
  private final NewMenuProcessor newMenuProcessor;

  public List<Menu> getMenuItems() {
    List<Menu> menuList = mealMenuCrawlerService.mealMenuCrawler();

    newMenuProcessor.processNewMenus(menuList);

    return menuList;
  }
}
