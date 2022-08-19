package com.example.bai_tap_co_ban_2.service;

import com.example.bai_tap_co_ban_2.common.ICRUDService;
import com.example.bai_tap_co_ban_2.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IServiceFood extends ICRUDService<Food> {
    Page<Food> findAllByNameContaining(String name, Pageable pageable);

    List<Food> findMaxPrice();

    List<Food> findMinPrice();

    Page<Food> findFoodByPriceBetween(double price1, double price2, Pageable pageable);


    Page<Food> findFoodByAvgPrice( Pageable pageable);

    Page<Food> findFoodByCategoryContaining ( String name, Pageable pageable);

}
