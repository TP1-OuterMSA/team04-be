package com.example.crawler.meal.component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
            .replaceAll("[\\u2600-\\u26FF]", "")
            .replaceAll("[\\uD83C-\\uDBFF\\uDC00-\\uDFFF]", "")
            .replaceAll("[♥★♡☺]", "")
            .replaceAll("\\[.*?]", "")
            .trim();
  }

  public List<String> splitMenuItems(String rawContent) {
    List<String> result = new ArrayList<>();
    Matcher matcher = Pattern.compile("[^\\s]+(?:\\s*\\([^)]*\\))?").matcher(rawContent.trim());

    while (matcher.find()) {
      String item = matcher.group().trim();

      // 괄호를 포함한 항목을 유지하면서도, 내부 단어를 특수문자 기준 분리
      if (!item.isEmpty()) {
        List<String> splitBySymbol = splitBySymbols(item);
        for (String split : splitBySymbol) {
          String cleaned = split.trim();
          if (!cleaned.isEmpty()) {
            result.add(cleaned);
          }
        }
      }
    }
    return result;
  }

  private List<String> splitBySymbols(String text) {
    // 괄호 안의 내용은 그대로 두되, 괄호 밖에서 특수문자 기준 분리
    // (괄호 안에 &가 있어도 분리하지 않음)
    // 자장면&초콜릿 → [자장면, 초콜릿]
    return Arrays.asList(text.split("[&/+@#*\\-_=~!%^$<>|\\\\]"));
  }



  public String normalizeFoodName(String raw) {
    return raw.trim().replaceAll("\\s+", "");
  }
}