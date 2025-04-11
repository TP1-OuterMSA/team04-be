package com.example.webscraper.meal.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)

public class MealItem {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;

  private String mealType;
  private String menuTitle;
  private String menuContent;
  private String extraInfo;

  @Builder.Default
  private LocalDateTime day = LocalDateTime.now();
}
