package com.example.crawler.meal.repository;

import com.example.crawler.meal.entity.FoodMenu;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FoodMenuRepository extends JpaRepository<FoodMenu, Integer> {
}
