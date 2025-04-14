package com.example.crawler.meal.service;

import com.example.crawler.meal.entity.MealItem;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class MealItemCrawlerService {
    private static final String TARGET_URL = "https://www.mju.ac.kr/mjukr/8595/subview.do";
    private static final String USER_AGENT = "Mozilla/5.0";

    public List<MealItem> mealItemCrawler() {
        List<MealItem> mealItemList = new ArrayList<>();
        try {
            Document doc = Jsoup.connect(TARGET_URL).userAgent(USER_AGENT).timeout(15000).get();
            Element weeklyMenuTable = findWeeklyMenuTable(doc);
            if (weeklyMenuTable == null) {
                log.warn("식단 테이블을 찾을 수 없음 - 페이지 구조 변경 가능성");
                return mealItemList;
            }
            parseTableRows(weeklyMenuTable.select("tbody tr"), mealItemList);
        } catch (IOException e) {
            log.error("식단 페이지 크롤링 중 오류 발생", e);
        }
        return mealItemList;
    }


    private Element findWeeklyMenuTable(Document doc) {
        return doc.select("table").stream()
            .filter(t -> t.selectFirst("caption") != null && t.selectFirst("caption").text().contains("일주일간 식단 안내"))
            .findFirst().orElse(null);
    }

    private void parseTableRows(Elements rows, List<MealItem> mealItemList) {
        String currentDay = null;
        for (Element row : rows) {
            Elements cells = row.select("th, td");
            if (cells.isEmpty()) continue;

            String firstCellText = cells.get(0).text().trim();
            if (firstCellText.matches("\\d{2}\\.\\d{2}.*\\([월화수목금]\\).*")) {
                currentDay = firstCellText;
                if (cells.size() >= 5) {
                    mealItemList.add(MealItem.of(currentDay, cells.get(1), cells.get(2), cells.get(3), cells.get(4)));
                }
            } else if (currentDay != null && cells.size() >= 4) {
                mealItemList.add(MealItem.of(currentDay, cells.get(0), cells.get(1), cells.get(2), cells.get(3)));
            }
        }
    }
}