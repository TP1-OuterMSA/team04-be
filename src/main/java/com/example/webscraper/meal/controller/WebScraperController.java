package com.example.webscraper.meal.controller;

import com.example.webscraper.meal.entity.MealItem;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.webscraper.meal.service.WebScraperService;

@RestController
@RequestMapping("/api/team04/scraper")
@RequiredArgsConstructor
public class WebScraperController {

    private final WebScraperService webScraperService;

    @GetMapping("/meal")
    public ResponseEntity<MealItem> getMeal() {
        return ResponseEntity.ok(
            webScraperService.getMeal());
    }
}
