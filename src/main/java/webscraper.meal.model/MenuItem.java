package webscraper.meal.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.jsoup.nodes.Element;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MenuItem {
    private String day;
    private String mealType;
    private String menuTitle;
    private String menuContent;
    private String extraInfo;

    public static MenuItem of(String day, Element mealType, Element title, Element content, Element extra) {
        return new MenuItem(
                day,
                mealType.text().trim(),
                title.text().trim(),
                content.text().trim(),
                extra.text().trim()
        );
    }

    public String getFormattedDate() {
        String datePart = day.split(" ")[0];
        String[] parts = datePart.split("\\.");
        int year = LocalDate.now().getYear();
        return String.format("%d-%s-%s", year, parts[0], parts[1]);
    }

    public String generateId() {
        String digit = switch (mealType) {
            case "조식" -> "1";
            case "중식" -> "2";
            case "석식" -> "3";
            default -> "0";
        };
        return getFormattedDate().replaceAll("-", "") + digit;
    }

    public String getFullContents() {
        return String.join(" ", menuTitle, menuContent, extraInfo).trim();
    }


    public String toMealJson() {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("id", Long.parseLong(generateId()));
        jsonMap.put("dayInfo", getFormattedDate());
        jsonMap.put("mealType", switch (mealType) {
            case "조식" -> "BREAKFAST";
            case "중식" -> "LUNCH";
            case "석식" -> "DINNER";
            default -> "UNKNOWN";
        });

        List<String> menuNames = new ArrayList<>();
        if (!menuTitle.isBlank()) menuNames.add(menuTitle);
        if (!menuContent.isBlank()) menuNames.add(menuContent);
        if (!extraInfo.isBlank()) menuNames.add(extraInfo);

        jsonMap.put("menuNames", menuNames);

        try {
            return mapper.writeValueAsString(jsonMap);
        } catch (JsonProcessingException e) {
            return "{}";
        }
    }
}
