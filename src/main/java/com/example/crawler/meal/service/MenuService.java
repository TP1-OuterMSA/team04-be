package com.example.crawler.meal.service;

import com.example.crawler.meal.component.MenuKafkaProducer;
import com.example.crawler.meal.dto.MenuParseResult;
import com.example.crawler.meal.entity.Meal;
import com.example.crawler.meal.entity.MealMenu;
import com.example.crawler.meal.entity.Menu;
import com.example.crawler.meal.repository.MealMenuRepository;
import com.example.crawler.meal.repository.MealRepository;
import com.example.crawler.meal.repository.MenuRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class MenuService {

  private final MenuKafkaProducer menuKafkaProducer;
  private final MealTableCrawlerService mealTableCrawlerService;

  private final MenuRepository menuRepository;
  private final MealRepository mealRepository;
  private final MealMenuRepository mealMenuRepository;

  public List<Menu> getMenuItems() {
    List<Menu> menuList = mealTableCrawlerService.mealTableCrawler();

    List<Long> existingIds = menuRepository.findAllIds();

    List<Menu> newMenus = menuList.stream()
        .filter(menu -> !existingIds.contains(menu.getMenuId()))
        .toList();

    if (!newMenus.isEmpty()) {
      menuRepository.saveAll(newMenus);

      for (Menu menu : newMenus) {
        String content = menu.getMenuContent();
        if (content != null && !content.isEmpty()) {
          String[] foods = content.split("\\s+");
          for (String foodName : foods) {
            if (foodName.length() > 1) {

              Meal meal = mealRepository.findByMealName(foodName)
                  .orElseGet(() -> mealRepository.save(Meal.of(foodName)));


              if (!mealMenuRepository.findByMenuAndMeal(menu, meal).isPresent()) {
                MealMenu mealMenu = new MealMenu(menu, meal);
                menu.getMealMenus().add(mealMenu);
                meal.getMealMenus().add(mealMenu);
                mealMenuRepository.save(mealMenu);
              }
            }
          }
        }
      }
    }

    return menuList;
  }
}