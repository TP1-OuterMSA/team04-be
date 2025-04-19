package com.example.crawler.meal.component;

import com.example.crawler.meal.service.MenuParseResult;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class MealTableParser {

  private final MealItemFormatter formatter;

  public List<MenuParseResult> parseToStructuredResult(Element tableElement) {
    if (tableElement == null) {
      throw new IllegalStateException("식단 데이터를 수집하지 못했습니다.");
    }

    List<MenuParseResult> result = new ArrayList<>();
    Elements rows = tableElement.select("tbody tr");

    String currentDayLabel = null;

    for (Element row : rows) {
      Elements cells = row.select("th, td");

      if (cells.isEmpty()) {
        continue;
      }

      String firstCellText = cells.get(0).text().trim();

      if (isDayRow(firstCellText)) {
        currentDayLabel = firstCellText;
        if (cells.size() >= 5) {
          result.add(parseItem(currentDayLabel, cells, 1));
        }
        continue;
      }

      if (currentDayLabel != null && cells.size() >= 4) {
        result.add(parseItem(currentDayLabel, cells, 0));
      }
    }

    return result;
  }

  private boolean isDayRow(String cellText) {
    return cellText.matches("\\d{2}\\.\\d{2}.*\\([월화수목금]\\).*");
  }

  private MenuParseResult parseItem(String rawDay, Elements cells, int offset) {
    LocalDate date = formatter.formatDate(rawDay);
    String mealType = formatter.formatMealType(cells.get(offset).text());
    String menuTitle = cells.get(offset + 1).text();
    String menuContent = formatter.formatMenuContent(cells.get(offset + 2).text());
    String extraInfo = cells.get(offset + 3).text();
    List<String> foodNames = formatter.splitMenuItems(menuContent);
    return new MenuParseResult(date, mealType, foodNames, menuTitle, menuContent, extraInfo);
  }
}