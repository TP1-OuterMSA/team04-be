package com.example.crawler.meal.component;

import com.example.crawler.meal.entity.MealItem;
import com.example.crawler.meal.vo.ExtraInfo;
import com.example.crawler.meal.vo.MealContent;
import com.example.crawler.meal.vo.MealDate;
import com.example.crawler.meal.vo.MealItemId;
import com.example.crawler.meal.vo.MealTitle;
import com.example.crawler.meal.vo.MealType;
import org.springframework.stereotype.Component;

@Component
public class MealItemAssembler {

    public MealItem assemble(
            MealItemId id,
            MealDate date,
            MealType type,
            MealTitle title,
            MealContent content,
            ExtraInfo extraInfo
    ) {
        return MealItem.of(
                id.value(),
                date.value(),
                type.value(),
                title.value(),
                content.value(),
                extraInfo.value()
        );
    }
}
