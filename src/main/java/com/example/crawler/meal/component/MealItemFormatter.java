package com.example.crawler.meal.component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import org.springframework.stereotype.Component;

@Component
public class MealItemFormatter {

  private static final DateTimeFormatter ID_DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd");

  public String formatId(String mealType, LocalDate date) {
    String typeDigit = switch (mealType) {
      case "breakfast" -> "1";
      case "lunch" -> "2";
      case "dinner" -> "3";
      default -> "0";
    };

    String datePart = date.format(ID_DATE_FORMATTER);
    return datePart + typeDigit;
  }

  public LocalDate formatDate(String rawDay) {
    String[] dateParts = extractDatePart(rawDay);

    int year = LocalDate.now().getYear();
    int month = Integer.parseInt(dateParts[0]);
    int day = Integer.parseInt(dateParts[1]);

    return LocalDate.of(year, month, day);
  }

  private String[] extractDatePart(String rawDay) {
    String dateSegment = rawDay.split(" ")[0];
    return dateSegment.split("\\.");
  }

  public String formatMealType(String rawText) {
    return switch (rawText.trim()) {
      case "조식" -> "breakfast";
      case "중식" -> "lunch";
      case "석식" -> "dinner";
      default -> "unknown";
    };
  }

  public String formatMenuContent(String rawContent) {
    return rawContent
            .replaceAll("[\\u2600-\\u26FF]", "") // 기호
            .replaceAll("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]", "") // 이모지
            .replaceAll("[♥★♡☺]", "")
            .replaceAll("\\[.*?]", "")
            .trim();
  }
}
