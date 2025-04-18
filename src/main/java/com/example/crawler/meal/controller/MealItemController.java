package com.example.crawler.meal.controller;

import com.example.crawler.meal.dto.MealItemResponse;
import com.example.crawler.meal.entity.MealItem;
import com.example.crawler.meal.service.MealItemService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/team4/meal")
@RequiredArgsConstructor
public class MealItemController {

    private final MealItemService mealItemService;

    @GetMapping("/items")
    public ResponseEntity<List<MealItemResponse>> getMealItems() {
        List<MealItem> items = mealItemService.getMealItems();

        List<MealItemResponse> responses = items.stream()
                .map(MealItemResponse::from)
                .toList();

        return ResponseEntity.ok(responses);
    }

}
