package com.example.crawler.meal.vo;

import java.util.Objects;

public class ExtraInfo {

    private final String info;

    public ExtraInfo(String info) {
        if (info == null) {
            throw new IllegalArgumentException("추가 정보는 null일 수 없습니다.");
        }
        this.info = info.trim();
    }

    public String value() {
        return info;
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof ExtraInfo other)) {
            return false;
        }
        return info.equals(other.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }
}
