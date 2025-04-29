package com.example.crawler.meal.repository;

import com.example.crawler.meal.entity.Menu;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MenuRepository extends JpaRepository<Menu, Long> {
    @Query("SELECT m.menuId FROM Menu m")
    List<Long> findAllMenuIds();
}
