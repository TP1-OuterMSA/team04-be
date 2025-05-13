package com.example.crawler.meal.service;

import com.example.crawler.meal.component.crawling.MealMenuExtractor;
import com.example.crawler.meal.component.crawling.MealMenuHtmlFetcher;
import com.example.crawler.meal.component.crawling.MealMenuParser;
import com.example.crawler.meal.entity.Menu;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class MealMenuCrawlerService {

  private static final List<String> TARGET_URLS = List.of(
      "https://www.mju.ac.kr/mjukr/8595/subview.do",
 "https://www.mju.ac.kr/mjukr/8595/subview.do?enc=Zm5jdDF8QEB8JTJGZGlldCUyRm1qdWtyJTJGMTAlMkZ2aWV3LmRvJTNGbW9uZGF5JTNEMjAyNS4wNS4xMiUyNndlZWslM0RwcmUlMjY%3D" ,
     "https://www.mju.ac.kr/mjukr/8595/subview.do?enc=Zm5jdDF8QEB8JTJGZGlldCUyRm1qdWtyJTJGMTAlMkZ2aWV3LmRvJTNGbW9uZGF5JTNEMjAyNS4wNS4wNSUyNndlZWslM0RwcmUlMjY%3D",
     "https://www.mju.ac.kr/mjukr/8595/subview.do?enc=Zm5jdDF8QEB8JTJGZGlldCUyRm1qdWtyJTJGMTAlMkZ2aWV3LmRvJTNGbW9uZGF5JTNEMjAyNS4wNC4yOCUyNndlZWslM0RwcmUlMjY%3D",
    "https://www.mju.ac.kr/mjukr/8595/subview.do?enc=Zm5jdDF8QEB8JTJGZGlldCUyRm1qdWtyJTJGMTAlMkZ2aWV3LmRvJTNGbW9uZGF5JTNEMjAyNS4wNC4yMSUyNndlZWslM0RwcmUlMjY%3D",
   "https://www.mju.ac.kr/mjukr/8595/subview.do?enc=Zm5jdDF8QEB8JTJGZGlldCUyRm1qdWtyJTJGMTAlMkZ2aWV3LmRvJTNGbW9uZGF5JTNEMjAyNS4wNC4xNCUyNndlZWslM0RwcmUlMjY%3D"
  );

  private final MealMenuHtmlFetcher mealMenuHtmlFetcher;
  private final MealMenuExtractor mealMenuExtractor;
  private final MealMenuParser mealMenuParser;

  public List<Menu> mealMenuCrawler() {
    List<Menu> totalMenuList = new ArrayList<>();

    for (String url : TARGET_URLS) {
      try {
        log.info("식단 크롤링 시작 - URL: {}", url);
        Document document = mealMenuHtmlFetcher.fetch(url);
        Element table = mealMenuExtractor.extractWeeklyMenu(document);
        List<Menu> weeklyMenus = mealMenuParser.parse(table);
        totalMenuList.addAll(weeklyMenus);
        log.info("식단 크롤링 성공 - {}건 수집됨", weeklyMenus.size());
      } catch (IOException e) {
        log.error("식단 크롤링 실패 - URL: {}", url, e);
      }
    }

    log.info("전체 식단 수집 완료 - 총 {}건", totalMenuList.size());
    return totalMenuList;
  }
}