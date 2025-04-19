package com.example.crawler.meal.service;

import com.example.crawler.meal.component.MealItemFormatter;
import com.example.crawler.meal.component.MealTableExtractor;
import com.example.crawler.meal.component.MealTableHtmlFetcher;
import com.example.crawler.meal.component.MealTableParser;
import com.example.crawler.meal.entity.Food;
import com.example.crawler.meal.entity.FoodMenu;
import com.example.crawler.meal.entity.Menu;
import com.example.crawler.meal.repository.FoodMenuRepository;
import com.example.crawler.meal.repository.FoodRepository;
import com.example.crawler.meal.repository.MenuRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MealCrawlingService {

    private final MealTableHtmlFetcher htmlFetcher;
    private final MealTableExtractor tableExtractor;
    private final MealTableParser tableParser;
    private final MealItemFormatter formatter;

    private final MenuRepository menuRepository;
    private final FoodRepository foodRepository;
    private final FoodMenuRepository foodMenuRepository;

    private static final String URL = "https://www.mju.ac.kr/mjukr/8595/subview.do";

    @Transactional
    public void crawlAndSave() {
        Document document = fetchDocument();
        Element table = tableExtractor.extractWeeklyMenuTable(document);

        if (table == null) {
            log.warn("식단 테이블을 찾지 못했습니다.");
            return;
        }

        List<MenuParseResult> results = tableParser.parseToStructuredResult(table);
        for (MenuParseResult result : results) {
            saveMenuAndFoods(result);
        }
    }

    private Document fetchDocument() {
        try {
            return htmlFetcher.fetch(URL);
        } catch (IOException e) {
            log.error("식단 HTML 문서 요청 실패", e);
            throw new RuntimeException(e);
        }
    }

    private void saveMenuAndFoods(MenuParseResult result) {
        Menu menu = menuRepository.findByDateAndMealType(result.date(), result.mealType())
                .orElseGet(() -> {
                    Menu newMenu = new Menu(result.date(), result.mealType());
                    newMenu.setMenuTitle(result.menuTitle());
                    newMenu.setMenuContent(result.menuContent());
                    newMenu.setExtraInfo(result.extraInfo());
                    return menuRepository.save(newMenu);
                });

        for (String rawFoodName : result.foodNames()) {
            String name = formatter.normalizeFoodName(rawFoodName);
            Food food = foodRepository.findByName(name)
                    .orElseGet(() -> foodRepository.save(new Food(name)));

            // ✅ 중복 확인 후 저장
            boolean alreadyExists = foodMenuRepository.findByMenuAndFood(menu, food).isPresent();
            if (!alreadyExists) {
                FoodMenu foodMenu = new FoodMenu(menu, food);
                foodMenuRepository.save(foodMenu);
            }
        }
    }
}
