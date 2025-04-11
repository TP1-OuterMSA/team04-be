package webscraper.meal.service;

import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import webscraper.meal.model.MenuItem;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
@Slf4j
@Component
public class MenuScraper {
    private static final String TARGET_URL = "https://www.mju.ac.kr/mjukr/8595/subview.do";
    private static final String USER_AGENT = "Mozilla/5.0";

    public List<MenuItem> scrap() {
        List<MenuItem> list = new ArrayList<>();

        try {
            Document doc = Jsoup.connect(TARGET_URL).userAgent(USER_AGENT).timeout(15000).get();
            Element weeklyMenuTable = doc.select("table").stream()
                    .filter(t -> {
                        Element cap = t.selectFirst("caption");
                        return cap != null && cap.text().contains("일주일간 식단 안내");
                    }).findFirst().orElse(null);

            if (weeklyMenuTable == null) {
                log.warn("식단 테이블을 찾을 수 없음 - 페이지 구조 변경 가능성");
                return list;
            }

            Elements rows = weeklyMenuTable.select("tbody tr");
            String currentDay = null;

            for (Element row : rows) {
                Elements cells = row.select("th, td");
                if (cells.isEmpty()) continue;

                String firstCellText = cells.get(0).text().trim();
                if (firstCellText.matches("\\d{2}\\.\\d{2}.*\\([월화수목금토일]\\).*")) {
                    currentDay = firstCellText;
                    if (cells.size() >= 5) {
                        list.add(MenuItem.of(currentDay, cells.get(1), cells.get(2), cells.get(3), cells.get(4)));
                    }
                } else if (currentDay != null && cells.size() >= 4) {
                    list.add(MenuItem.of(currentDay, cells.get(0), cells.get(1), cells.get(2), cells.get(3)));
                }
            }

        } catch (IOException e) {
            log.error("스크래핑 오류: {}", e.getMessage());
        }

        return list;
    }
}
