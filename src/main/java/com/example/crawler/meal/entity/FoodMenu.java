package com.example.crawler.meal.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "food_menu", uniqueConstraints = @UniqueConstraint(columnNames = {"menu_id", "food_id"}))
public class FoodMenu {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "menu_id")
    private Menu menu;

    @ManyToOne(optional = false)
    @JoinColumn(name = "food_id")
    private Food food;

    protected FoodMenu() {
    }

    public FoodMenu(Menu menu, Food food) {
        this.menu = menu;
        this.food = food;
    }

    public Integer getId() {
        return id;
    }

    public Menu getMenu() {
        return menu;
    }

    public void setMenu(Menu menu) {
        this.menu = menu;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
