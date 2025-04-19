package com.example.crawler.meal.repository;

import com.example.crawler.meal.entity.Food;
import com.example.crawler.meal.entity.FoodMenu;
import com.example.crawler.meal.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Integer> {
    Optional<FoodMenu> findByMenuAndFood(Menu menu, Food food);
}
