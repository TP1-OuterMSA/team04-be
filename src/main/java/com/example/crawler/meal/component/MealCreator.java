package com.example.crawler.meal.component;

import com.example.crawler.meal.entity.Meal;
import com.example.crawler.meal.repository.MealRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealCreator {

    private final MealRepository mealRepository;

    public Meal findOrCreateMeal(String mealName) {
        return mealRepository.findByMealName(mealName)
                .orElseGet(() -> mealRepository.save(Meal.of(mealName)));
    }
}
