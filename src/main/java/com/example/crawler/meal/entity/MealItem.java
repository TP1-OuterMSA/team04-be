package com.example.crawler.meal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MealItem {

  @Id
  private int id;

  private LocalDate day;
  private String mealType;
  private String menuTitle;
  private String menuContent;
  private String extraInfo;

  public static MealItem of(
          int id,
          LocalDate day,
          String mealType,
          String menuTitle,
          String menuContent,
          String extraInfo
  ) {
    return MealItem.builder()
            .id(id)
            .day(day)
            .mealType(mealType)
            .menuTitle(menuTitle)
            .menuContent(menuContent)
            .extraInfo(extraInfo)
            .build();
  }

  // 게터 대신 명확한 이름의 값 반환 메서드
  public int id() {
    return id;
  }

  public LocalDate day() {
    return day;
  }

  public String mealType() {
    return mealType;
  }

  public String menuTitle() {
    return menuTitle;
  }

  public String menuContent() {
    return menuContent;
  }

  public String extraInfo() {
    return extraInfo;
  }
}
