package com.example.crawler.meal.vo;

import java.util.List;
import java.util.stream.Collectors;

public class MealItemIdList {

    private final List<MealItemId> idList;

    public MealItemIdList(List<Integer> rawIdList) {
        this.idList = rawIdList.stream()
                .map(MealItemId::new)
                .collect(Collectors.toUnmodifiableList());
    }

    public List<MealItemId> values() {
        return idList;
    }

    public boolean contains(MealItemId id) {
        return idList.contains(id);
    }

    public int size() {
        return idList.size();
    }

    public boolean isEmpty() {
        return idList.isEmpty();
    }
}
