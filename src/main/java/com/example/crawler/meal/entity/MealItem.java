package com.example.crawler.meal.entity;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import java.util.*;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MealItem {
  @Id
  private long id;
  private String day;
  private String mealType;
  private String menuContent;
  private String menuTitle;
  private String extraInfo;

  private static final ObjectMapper mapper = new ObjectMapper();

  public static MealItem of(String day, Element mealType, Element title, Element content, Element extra) {
    String cleanedContent = content.text()
        .replaceAll("[\\u2600-\\u26FF]", "")  // Unicode 블록: 날씨 아이콘 등 제거
        .replaceAll("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]", "")  // 이모티콘(UTF-16) 제거
        .replaceAll("[♥★♡☺]", "")  // 특정 특수기호 제거 (예: ♥, ★, ♡, ☺)
        .replaceAll("\\[.*?\\]", "")  // [ ** day]와 같은 패턴 제거
        .trim();  // 앞뒤 공백 제거

    MealItem temp = MealItem.builder()
        .day(day)
        .mealType(mealType.text().trim())
        .menuTitle(title.text().trim())
        .menuContent(cleanedContent)
        .extraInfo(extra.text().trim())
        .build();
    temp.id = Long.parseLong(temp.generateId());
    return temp;
  }


  public String getFormattedDate() {//날짜 정규화
    String[] parts = day.split(" ")[0].split("\\.");
    return String.format("%d-%s-%s", LocalDate.now().getYear(), parts[0], parts[1]);
  }

  public String generateId() {//mealType과 날짜에 따라 고유ID생성
    String digit = switch (mealType) {
      case "조식" -> "1";
      case "중식" -> "2";
      case "석식" -> "3";
      default -> "0";
    };
    return getFormattedDate().replaceAll("-", "") + digit;
  }

  public Map<String, Object> toSimpleJson() {
    Map<String, Object> map = new HashMap<>();
    map.put("mealType", mealType);

    List<String> menuNames = Arrays.stream(menuContent.split("\\s+"))
        .filter(s -> !s.isBlank())
        .toList();

    map.put("menuContents", menuNames);
    return map;
  }
}