package com.example.crawler.meal.component;

import com.example.crawler.meal.entity.MealItem;
import com.example.crawler.meal.vo.MealItemId;
import com.example.crawler.meal.vo.MealItemList;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MealItemFilter {

    public List<MealItem> filterNewItems(MealItemList mealItemList, List<MealItemId> existingIds) {
        List<MealItem> result = new ArrayList<>();

        for (MealItem item : mealItemList.items()) {
            MealItemId itemId = new MealItemId(item.id());
            if (isNew(itemId, existingIds)) {
                result.add(item);
            }
        }

        return result;
    }

    private boolean isNew(MealItemId candidate, List<MealItemId> existingIds) {
        for (MealItemId existingId : existingIds) {
            if (candidate.equals(existingId)) {
                return false;
            }
        }
        return true;
    }
}
