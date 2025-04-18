package com.example.crawler.meal.vo;

import java.util.Objects;

public class MealContent {

    private final String content;

    public MealContent(String content) {
        if (content == null || content.isBlank()) {
            throw new IllegalArgumentException("식단 내용은 비어 있을 수 없습니다.");
        }
        this.content = content.trim();
    }

    public String value() {
        return content;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MealContent other)) {
            return false;
        }
        return content.equals(other.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}
