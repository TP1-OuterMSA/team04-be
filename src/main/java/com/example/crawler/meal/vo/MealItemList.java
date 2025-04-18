package com.example.crawler.meal.vo;

import com.example.crawler.meal.entity.MealItem;
import java.util.Collections;
import java.util.List;

public class MealItemList {

    private final List<MealItem> internalList;

    public MealItemList(List<MealItem> items) {
        this.internalList = Collections.unmodifiableList(items);
    }

    public List<MealItem> items() {
        return internalList;
    }

    public int size() {
        return internalList.size();
    }

    public boolean isEmpty() {
        return internalList.isEmpty();
    }
}
