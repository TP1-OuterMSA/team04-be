package com.example.crawler.meal.component;

import com.example.crawler.meal.entity.Meal;
import com.example.crawler.meal.entity.MealMenu;
import com.example.crawler.meal.entity.Menu;
import com.example.crawler.meal.repository.MealMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealMenuCreator {

    private final MealMenuRepository mealMenuRepository;

    public void createMealMenuIfNotExist(Menu menu, Meal meal) {
        boolean exists = mealMenuRepository.findByMenuAndMeal(menu, meal).isPresent();
        if (!exists) {
            MealMenu mealMenu = new MealMenu(menu, meal);
            mealMenuRepository.save(mealMenu);
            // 양방향 매핑 설정
            menu.getMealMenus().add(mealMenu);
            meal.getMealMenus().add(mealMenu);
        }
    }
}
