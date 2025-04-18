package com.example.crawler.meal.vo;

import java.util.Objects;

public class MealType {

    private final String type;

    public MealType(String type) {
        if (type == null || type.isBlank()) {
            throw new IllegalArgumentException("식사 타입은 필수입니다.");
        }
        this.type = type;
    }

    public String value() {
        return type;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MealType other)) {
            return false;
        }
        return type.equals(other.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(type);
    }
}
