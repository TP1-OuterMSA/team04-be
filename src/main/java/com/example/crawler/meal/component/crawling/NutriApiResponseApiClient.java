package com.example.crawler.meal.component.crawling;

import com.example.crawler.meal.dto.NutriApiResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@RequiredArgsConstructor
@Slf4j
public class NutriApiResponseApiClient {

    private static final String SERVICE_KEY = "aFwuKc+JUJst/AB9NNSc/rtvgVlPnxvd453pNQK79v3sQ7lsY1Gb77IRpUn+LRsTub/6gVEX2MsEfn5A97l3IA==";

    private final WebClient webClient = WebClient.builder()
        .baseUrl("https://api.data.go.kr/openapi/tn_pubr_public_nutri_food_info_api")
        .build();

    public NutriApiResponse.Item fetchNutritionInfo(String foodName) {
        try {
            NutriApiResponse response = webClient.get()
                .uri(uriBuilder -> uriBuilder
                    .queryParam("serviceKey", SERVICE_KEY)
                    .queryParam("pageNo", 1)
                    .queryParam("numOfRows", 1)
                    .queryParam("type", "json")
                    .queryParam("foodNm", foodName)
                    .build())
                .retrieve()
                .bodyToMono(NutriApiResponse.class)
                .block();

            List<NutriApiResponse.Item> items = response.getResponse().getBody().getItems()
                .getItem();
            if (items != null && !items.isEmpty()) {
                NutriApiResponse.Item item = items.get(0);
                log.info("✅ 영양 정보 - {}: 칼로리: {}, 탄수화물: {}, 단백질: {}, 지방: {}",
                    item.getFoodName(),
                    item.getCalories(),
                    item.getCarb(),
                    item.getProtein(),
                    item.getFat());
                return item;
            } else {
                log.warn("⚠️ 영양 정보 없음: {}", foodName);
                return null;
            }

        } catch (Exception e) {
            log.error("API 호출 오류: {}", foodName, e);
            return null;
        }
    }
}