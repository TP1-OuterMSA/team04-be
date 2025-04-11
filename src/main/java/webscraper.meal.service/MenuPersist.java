package webscraper.meal.service;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import webscraper.meal.model.MenuItem;

@Component
@RequiredArgsConstructor
public class MenuPersist {
    private final JdbcTemplate jdbcTemplate;

    public void save(MenuItem item) {
        String sql = "INSERT INTO menu (id, date, type, contents) VALUES (?, ?, ?, ?) " +
                "ON DUPLICATE KEY UPDATE contents = VALUES(contents)";

        jdbcTemplate.update(sql,
                item.generateId(),
                item.getFormattedDate(),
                item.getMealType(),
                item.getFullContents());
    }
}

