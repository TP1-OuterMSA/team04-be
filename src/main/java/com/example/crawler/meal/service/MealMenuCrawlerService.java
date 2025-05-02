package com.example.crawler.meal.service;

import com.example.crawler.meal.component.crawling.MealMenuExtractor;
import com.example.crawler.meal.component.crawling.MealMenuHtmlFetcher;
import com.example.crawler.meal.component.crawling.MealMenuParser;
import com.example.crawler.meal.entity.Menu;
import java.io.IOException;
import java.util.Collections;
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

  private static final String TARGET_URL = "https://www.mju.ac.kr/mjukr/8595/subview.do";

  private final MealMenuHtmlFetcher mealMenuHtmlFetcher;
  private final MealMenuExtractor mealMenuExtractor;
  private final MealMenuParser mealMenuParser;

  public List<Menu> mealMenuCrawler() {
    try {
      Document document = mealMenuHtmlFetcher.fetch(TARGET_URL);
      Element table = mealMenuExtractor.extractWeeklyMenu(document);
      return mealMenuParser.parse(table);
    } catch (IOException e) {
      log.error("식단 테이블 크롤링 중 오류 발생", e);
      return Collections.emptyList();
    }
  }
}
