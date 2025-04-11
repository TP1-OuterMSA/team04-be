package com.example.webscraper.meal.controller;

import com.example.webscraper.meal.entity.MealItem;
import com.example.webscraper.meal.service.MealItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team04/scraper")
@RequiredArgsConstructor
public class MealItemController {

    private final MealItemService mealItemService;

    @GetMapping("/meal")
    public ResponseEntity<MealItem> getMeal() {
        return ResponseEntity.ok();
    }
}
