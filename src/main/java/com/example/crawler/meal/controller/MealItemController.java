package com.example.crawler.meal.controller;

import com.example.crawler.meal.dto.MealItemResponse;
import com.example.crawler.meal.entity.FoodMenu;
import com.example.crawler.meal.entity.Menu;
import com.example.crawler.meal.repository.FoodMenuRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/team4/meal")
@RequiredArgsConstructor
public class MealItemController {

    private final FoodMenuRepository foodMenuRepository;

    private static final Map<String, Integer> MEAL_TYPE_ORDER = Map.of(
            "breakfast", 1,
            "lunch", 2,
            "dinner", 3
    );

    @GetMapping("/items")
    public ResponseEntity<List<MealItemResponse>> getMealItems() {
        LocalDate today = LocalDate.now();
        LocalDate startOfWeek = today.with(DayOfWeek.MONDAY);
        LocalDate endOfWeek = today.with(DayOfWeek.FRIDAY);

        List<FoodMenu> foodMenus = foodMenuRepository.findAll().stream()
                .filter(fm -> {
                    LocalDate date = fm.getMenu().getDate();
                    return !date.isBefore(startOfWeek) && !date.isAfter(endOfWeek);
                })
                .toList();

        Map<Integer, List<FoodMenu>> grouped = foodMenus.stream()
                .collect(Collectors.groupingBy(fm -> fm.getMenu().getId()));

        List<MealItemResponse> responses = new ArrayList<>();

        for (Map.Entry<Integer, List<FoodMenu>> entry : grouped.entrySet()) {
            Menu menu = entry.getValue().get(0).getMenu();
            int id = formatMenuId(menu.getDate(), menu.getMealType());

            responses.add(new MealItemResponse(
                    id,
                    menu.getDate(),
                    menu.getMealType(),
                    menu.getMenuContent(),
                    menu.getMenuTitle(),
                    menu.getExtraInfo()
            ));
        }

        responses.sort(Comparator
                .comparing(MealItemResponse::getDay)
                .thenComparing(r -> MEAL_TYPE_ORDER.getOrDefault(r.getMealType(), 0)));

        return ResponseEntity.ok(responses);
    }

    private int formatMenuId(LocalDate date, String mealType) {
        String typeDigit = switch (mealType) {
            case "breakfast" -> "1";
            case "lunch" -> "2";
            case "dinner" -> "3";
            default -> "0";
        };
        return Integer.parseInt(date.format(DateTimeFormatter.ofPattern("yyyyMMdd")) + typeDigit);
    }
}
