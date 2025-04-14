package com.example.crawler.meal.repository;

import com.example.crawler.meal.entity.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MealItemRepository extends JpaRepository<MealItem, Long> {

}
