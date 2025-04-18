package com.example.crawler.meal.component;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
public class MealTableExtractor {

    public Element extractWeeklyMenuTable(Document document) {
        Elements tables = document.select("table");

        for (Element table : tables) {
            Element caption = table.selectFirst("caption");

            if (caption != null && caption.text().contains("일주일간 식단 안내")) {
                return table;
            }
        }

        return null;
    }
}
