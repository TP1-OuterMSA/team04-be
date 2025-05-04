package com.example.crawler.meal.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class NutriApiResponse {

    private Response response;

    @Data
    public static class Response {
        private Body body;
    }

    @Data
    public static class Body {
        private Items items;
    }

    @Data
    public static class Items {
        private java.util.List<Item> item;
    }

    @Data
    public static class Item {
        @JsonProperty("foodNm")
        private String foodName;

        @JsonProperty("enerc")  // 칼로리
        private String calories;

        @JsonProperty("prot")   // 단백질(g)
        private String protein;

        @JsonProperty("fatce")  // 지방(g)
        private String fat;

        @JsonProperty("chocdf") // 탄수화물(g)
        private String carb;
    }
}
