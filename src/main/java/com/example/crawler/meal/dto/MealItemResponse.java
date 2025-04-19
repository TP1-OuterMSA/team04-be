package com.example.crawler.meal.dto;

import com.example.crawler.meal.entity.FoodMenu;

import java.time.LocalDate;

public class MealItemResponse {

    private final int id;
    private final LocalDate day;
    private final String mealType;
    private final String menuTitle;
    private final String menuContent;
    private final String extraInfo;

    public MealItemResponse(
            int id,
            LocalDate day,
            String mealType,
            String menuTitle,
            String menuContent,
            String extraInfo
    ) {
        this.id = id;
        this.day = day;
        this.mealType = mealType;
        this.menuTitle = menuTitle;
        this.menuContent = menuContent;
        this.extraInfo = extraInfo;
    }

    public static MealItemResponse from(FoodMenu foodMenu) {
        return new MealItemResponse(
                foodMenu.getMenu().getId(),
                foodMenu.getMenu().getDate(),
                foodMenu.getMenu().getMealType(),
                "", // menuTitle 없음
                foodMenu.getFood().getName(), // 식단 항목명
                ""  // extraInfo 없음
        );
    }

    public int getId() {
        return id;
    }

    public LocalDate getDay() {
        return day;
    }

    public String getMealType() {
        return mealType;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public String getMenuContent() {
        return menuContent;
    }

    public String getExtraInfo() {
        return extraInfo;
    }
}
