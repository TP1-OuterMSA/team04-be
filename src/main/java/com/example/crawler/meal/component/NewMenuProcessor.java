package com.example.crawler.meal.component;

import com.example.crawler.meal.entity.Meal;
import com.example.crawler.meal.entity.Menu;
import com.example.crawler.meal.repository.MenuRepository;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.time.LocalDate;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewMenuProcessor {

    private final MenuRepository menuRepository;
    private final MealCreator mealCreator;
    private final MealMenuCreator mealMenuCreator;
    private final MealCategory mealCategory;  // MealCategory 주입

    public void processNewMenus(List<Menu> menus) {
        Set<String> existingDateAndTypes = menuRepository.findAll().stream()
            .map(menu -> createDateAndTypeKey(menu.getDate(), menu.getMealType()))
            .collect(Collectors.toSet());

        List<Menu> newMenus = menus.stream()
            .filter(menu -> !existingDateAndTypes.contains(
                createDateAndTypeKey(menu.getDate(), menu.getMealType())))
            .toList();

        if (!newMenus.isEmpty()) {
            menuRepository.saveAll(newMenus);

            for (Menu menu : newMenus) {
                String content = menu.getMenuContent();
                if (content != null && !content.isEmpty()) {

                    String[] foodNames = content.split("\\s+");
                    for (String foodName : foodNames) {
                        if (foodName.length() > 1) {

                            String category = mealCategory.classify(foodName);


                            Meal meal = mealCreator.findOrCreateMeal(foodName);
                            meal.setMealCategory(category);  // 카테고리 설정


                            mealMenuCreator.createMealMenuIfNotExist(menu, meal);
                        }
                    }
                }
            }
        }
    }

    private String createDateAndTypeKey(LocalDate date, String mealType) {
        return date + "_" + mealType;
    }
}
