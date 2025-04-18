package com.example.crawler.meal.component;

import com.example.crawler.meal.entity.MealItem;
import com.example.crawler.meal.vo.ExtraInfo;
import com.example.crawler.meal.vo.MealContent;
import com.example.crawler.meal.vo.MealDate;
import com.example.crawler.meal.vo.MealItemId;
import com.example.crawler.meal.vo.MealTitle;
import com.example.crawler.meal.vo.MealType;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MealTableParser {

  private final MealItemFormatter formatter;
  private final MealItemAssembler assembler;

  public List<MealItem> parse(Element tableElement) {
    if (tableElement == null) {
      throw new IllegalStateException("식단 데이터를 수집하지 못했습니다.");
    }

    List<MealItem> result = new ArrayList<>();
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

  private MealItem parseItem(String rawDay, Elements cells, int offset) {
    MealDate date = new MealDate(formatter.formatDate(rawDay));
    MealType type = new MealType(formatter.formatMealType(cells.get(offset).text()));
    MealTitle title = new MealTitle(cells.get(offset + 1).text());
    MealContent content = new MealContent(formatter.formatMenuContent(cells.get(offset + 2).text()));
    ExtraInfo extra = new ExtraInfo(cells.get(offset + 3).text());
    MealItemId id = new MealItemId(Integer.parseInt(formatter.formatId(type.value(), date.value())));

    return assembler.assemble(id, date, type, title, content, extra);
  }
}
