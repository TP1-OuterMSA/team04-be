package com.example.crawler.meal.entity;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "food", uniqueConstraints = @UniqueConstraint(columnNames = "name"))
public class Food {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String name;

    private String category;
    private String nutrition;
    private Integer calorie;
    private String allergy;

    @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodMenu> foodMenus = new ArrayList<>();

    protected Food() {
    }

    // 기존 이름만 받는 생성자
    public Food(String name) {
        this.name = name;
    }

    // 새로운 category 포함 생성자
    public Food(String name, String category) {
        this.name = name;
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public String getNutrition() {
        return nutrition;
    }

    public Integer getCalorie() {
        return calorie;
    }

    public String getAllergy() {
        return allergy;
    }

    public List<FoodMenu> getFoodMenus() {
        return foodMenus;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
