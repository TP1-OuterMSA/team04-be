package com.example.crawler.meal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Meal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer mealId;

    @Column(nullable = false)
    private String mealName;

    private String mealCategory;
    private String nutrition;
    private Integer calorie;
    private String allergy;

    @OneToMany(mappedBy = "meal", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference("meal-mealMenu")
    private List<MealMenu> mealMenus = new ArrayList<>();


    public static Meal of(String foodName) {
        Meal meal = new Meal();
        meal.setMealName(foodName);
        meal.setMealMenus(new ArrayList<>());
        return meal;
    }

    public static Meal of(String name, String category) {
        return Meal.builder().mealName(name).mealCategory(category).build();
    }
}