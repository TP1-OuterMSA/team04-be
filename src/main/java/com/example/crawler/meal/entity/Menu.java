package com.example.crawler.meal.entity;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "menu")
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private LocalDate date;

    @Column(name = "meal_type", nullable = false)
    private String mealType;

    @Column(name = "menu_title")
    private String menuTitle;

    @Column(name = "menu_content", columnDefinition = "TEXT")
    private String menuContent;

    @Column(name = "extra_info")
    private String extraInfo;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<FoodMenu> foodMenus = new ArrayList<>();

    protected Menu() {
    }

    public Menu(LocalDate date, String mealType) {
        this.date = date;
        this.mealType = mealType;
    }

    public Integer getId() {
        return id;
    }

    public LocalDate getDate() {
        return date;
    }

    public String getMealType() {
        return mealType;
    }

    public String getMenuTitle() {
        return menuTitle;
    }

    public void setMenuTitle(String menuTitle) {
        this.menuTitle = menuTitle;
    }

    public String getMenuContent() {
        return menuContent;
    }

    public void setMenuContent(String menuContent) {
        this.menuContent = menuContent;
    }

    public String getExtraInfo() {
        return extraInfo;
    }

    public void setExtraInfo(String extraInfo) {
        this.extraInfo = extraInfo;
    }

    public List<FoodMenu> getFoodMenus() {
        return foodMenus;
    }

    public void addFoodMenu(FoodMenu foodMenu) {
        foodMenus.add(foodMenu);
        foodMenu.setMenu(this);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Menu other)) return false;
        return Objects.equals(id, other.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}