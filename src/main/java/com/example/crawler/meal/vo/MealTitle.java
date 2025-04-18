package com.example.crawler.meal.vo;

import java.util.Objects;

public class MealTitle {

    private final String title;

    public MealTitle(String title) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("식단 제목은 필수입니다.");
        }
        this.title = title.trim();
    }

    public String value() {
        return title;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MealTitle other)) {
            return false;
        }
        return title.equals(other.title);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title);
    }
}
