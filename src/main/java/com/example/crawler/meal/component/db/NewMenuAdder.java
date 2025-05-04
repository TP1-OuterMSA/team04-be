package com.example.crawler.meal.component.db;

import com.example.crawler.meal.component.crawling.NutriApiResponseApiClient;
import com.example.crawler.meal.dto.NutriApiResponse;
import com.example.crawler.meal.entity.Meal;
import com.example.crawler.meal.entity.MealMenu;
import com.example.crawler.meal.entity.Menu;
import com.example.crawler.meal.repository.MealMenuRepository;
import com.example.crawler.meal.repository.MealRepository;
import com.example.crawler.meal.repository.MenuRepository;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewMenuAdder {

    private final MenuRepository menuRepository;
    private final MealRepository mealRepository;
    private final MealMenuRepository mealMenuRepository;
    private final MealCategory mealCategory;
    private final NutriApiResponseApiClient NutriApiResponseApiClient;
    public List<Menu> addNewMenus(List<Menu> menus) {
        Set<String> existingKeys = menuRepository.findAll().stream()
            .map(menu -> menu.getDate() + "_" + menu.getMealType())
            .collect(Collectors.toSet());

        List<Menu> newMenus = menus.stream()
            .filter(menu -> !existingKeys.contains(menu.getDate() + "_" + menu.getMealType()))
            .toList();

        if (!newMenus.isEmpty()) {
            List<Menu> savedMenus = saveMenus(newMenus);
            saveMealsAndMealMenus(savedMenus);
            return savedMenus;
        }
        return List.of();
    }


    private List<Menu> saveMenus(List<Menu> newMenus) {
        return menuRepository.saveAll(newMenus);
    }

    private void saveMealsAndMealMenus(List<Menu> menus) {
        for (Menu menu : menus) {
            for (String foodName : extractFoodNames(menu.getMenuContent())) {
                String category = mealCategory.classify(foodName);
                Meal meal = findOrCreateMeal(foodName);
                meal.setMealCategory(category);
                createMealMenuIfNotExists(menu, meal);
            }
        }
    }

    private List<String> extractFoodNames(String content) {
        if (content == null || content.isBlank())
            return List.of();
        return Arrays.stream(content.split("\\s+"))
            .filter(name -> name.length() > 1)
            .toList();
    }

    private Meal findOrCreateMeal(String mealName) {
        return mealRepository.findByMealName(mealName)
            .orElseGet(() -> {
                Meal newMeal = Meal.of(mealName);
                NutriApiResponse.Item nutrition = NutriApiResponseApiClient.fetchNutritionInfo(mealName);

                if (nutrition != null) {
                    newMeal.setCalorie(parseDoubleOrZero(nutrition.getCalories()));
                    newMeal.setCarb(parseDoubleOrZero(nutrition.getCarb()));
                    newMeal.setProtein(parseDoubleOrZero(nutrition.getProtein()));
                    newMeal.setFat(parseDoubleOrZero(nutrition.getFat()));
                }

                return mealRepository.save(newMeal);
            });
    }

    private Double parseDoubleOrZero(String value) {
        try {
            if (value == null || value.isBlank()) return 0.0;
            return Double.parseDouble(value.trim());
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }

    private void createMealMenuIfNotExists(Menu menu, Meal meal) {
        if (mealMenuRepository.findByMenuAndMeal(menu, meal).isEmpty()) {
            MealMenu mealMenu = new MealMenu(menu, meal);
            mealMenuRepository.save(mealMenu);
            menu.getMealMenus().add(mealMenu);
            meal.getMealMenus().add(mealMenu);
        }
    }
}