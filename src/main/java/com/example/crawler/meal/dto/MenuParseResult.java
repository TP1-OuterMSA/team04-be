package com.example.crawler.meal.dto;

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