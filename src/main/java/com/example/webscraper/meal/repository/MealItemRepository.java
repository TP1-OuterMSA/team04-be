package com.example.webscraper.meal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import webscraper.meal.model.MenuItem;

public interface MealItemRepository extends JpaRepository<MenuItem, Long> {

}
