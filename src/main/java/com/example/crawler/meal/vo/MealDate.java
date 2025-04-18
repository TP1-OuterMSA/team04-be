package com.example.crawler.meal.vo;

import java.time.LocalDate;
import java.util.Objects;

public class MealDate {

    private final LocalDate date;

    public MealDate(LocalDate date) {
        if (date == null) {
            throw new IllegalArgumentException("날짜는 null일 수 없습니다.");
        }
        this.date = date;
    }

    public LocalDate value() {
        return date;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MealDate other)) {
            return false;
        }
        return date.equals(other.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(date);
    }
}
