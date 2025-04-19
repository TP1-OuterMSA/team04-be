package com.example.crawler.meal.repository;

import com.example.crawler.meal.entity.Food;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    Optional<Food> findByName(String name);
}
