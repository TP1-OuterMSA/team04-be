package com.example.crawler.meal.vo;

import java.util.Objects;

public class MealItemId {

    private final int value;

    public MealItemId(int value) {
        this.value = value;
    }

    public boolean equals(MealItemId other) {
        return this.value == other.value;
    }

    public int value() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MealItemId other)) {
            return false;
        }
        return equals(other);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
}
