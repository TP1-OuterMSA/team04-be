package com.example.crawler.meal.repository;

import com.example.crawler.meal.entity.MealItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MealItemRepository extends JpaRepository<MealItem, Long> {

  @Query("SELECT m.id FROM MealItem m")
  List<Integer> findAllIds();
}
