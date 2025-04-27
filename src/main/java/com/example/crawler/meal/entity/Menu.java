package com.example.crawler.meal.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Builder
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Menu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long menuId;

    private LocalDate date;
    private String mealType;
    private String menuTitle;
    private String menuContent;
    private String extraInfo;

    @OneToMany(mappedBy = "menu", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    @JsonManagedReference
    private List<MealMenu> mealMenus = new ArrayList<>();

    public void addMealMenu(MealMenu mealMenu) {
        mealMenus.add(mealMenu);
        mealMenu.setMenu(this);
    }

    public void removeMealMenu(MealMenu mealMenu) {
        mealMenus.remove(mealMenu);
        mealMenu.setMenu(null);
    }

    public Menu(LocalDate date, String mealType) {
        this.date = date;
        this.mealType = mealType;
    }

    public static Menu of(LocalDate date, String mealType, String menuTitle, String menuContent, String extraInfo) {
        return Menu.builder()
            .date(date)
            .mealType(mealType)
            .menuTitle(menuTitle)
            .menuContent(menuContent)
            .extraInfo(extraInfo)
            .build();
    }
}