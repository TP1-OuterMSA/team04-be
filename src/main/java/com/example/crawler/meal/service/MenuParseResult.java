package com.example.crawler.meal.service;

import java.time.LocalDate;
import java.util.List;

public record MenuParseResult(
        LocalDate date,
        String mealType,
        List<String> foodNames,
        String menuTitle,
        String menuContent,
        String extraInfo
) {
}