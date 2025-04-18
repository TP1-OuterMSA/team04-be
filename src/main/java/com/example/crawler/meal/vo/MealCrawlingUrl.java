package com.example.crawler.meal.vo;

import java.util.Objects;

public class MealCrawlingUrl {

    private final String url;

    public MealCrawlingUrl(String url) {
        if (url == null || url.isBlank()) {
            throw new IllegalArgumentException("크롤링 URL은 필수입니다.");
        }
        this.url = url;
    }

    public String value() {
        return url;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MealCrawlingUrl other)) {
            return false;
        }
        return url.equals(other.url);
    }

    @Override
    public int hashCode() {
        return Objects.hash(url);
    }
}
