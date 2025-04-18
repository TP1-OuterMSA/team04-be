package com.example.crawler.meal.service;

import com.example.crawler.meal.component.MealTableExtractor;
import com.example.crawler.meal.component.MealTableHtmlFetcher;
import com.example.crawler.meal.component.MealTableParser;
import com.example.crawler.meal.entity.MealItem;
import com.example.crawler.meal.vo.MealCrawlingUrl;
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
public class MealTableCrawlerService {

  private final MealTableHtmlFetcher htmlFetcher;
  private final MealTableExtractor tableExtractor;
  private final MealTableParser tableParser;

  private final MealCrawlingUrl crawlingUrl = new MealCrawlingUrl("https://www.mju.ac.kr/mjukr/8595/subview.do");

  public List<MealItem> mealTableCrawler() {
    Document document = fetchDocument();
    Element table = extractTable(document);
    return parseItems(table);
  }

  private Document fetchDocument() {
    try {
      return htmlFetcher.fetch(crawlingUrl.value());
    } catch (IOException e) {
      log.error("식단 HTML 문서 요청 실패", e);
      return null;
    }
  }

  private Element extractTable(Document document) {
    if (document == null) {
      return null;
    }
    return tableExtractor.extractWeeklyMenuTable(document);
  }

  private List<MealItem> parseItems(Element table) {
    if (table == null) {
      return Collections.emptyList();
    }
    return tableParser.parse(table);
  }
}
