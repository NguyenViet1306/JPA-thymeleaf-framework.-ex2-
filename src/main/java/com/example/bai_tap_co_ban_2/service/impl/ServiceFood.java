package com.example.bai_tap_co_ban_2.service.impl;

import com.example.bai_tap_co_ban_2.model.Food;
import com.example.bai_tap_co_ban_2.repository.FoodRepository;
import com.example.bai_tap_co_ban_2.service.IServiceFood;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


import java.util.List;
import java.util.Optional;

public class ServiceFood implements IServiceFood {
    @Autowired
    private FoodRepository foodRepository;

    @Override
    public Page<Food> findAll(Pageable pageable) {
        return foodRepository.findAll(pageable);
    }

    @Override
    public Food save(Food food) {
        return foodRepository.save(food);
    }

    @Override
    public void delete(Long id) {
        foodRepository.deleteById(id);
    }

    @Override
    public Optional<Food> findById(Long id) {
        return foodRepository.findById(id);
    }

    @Override
    public Page<Food> findAllByNameContaining(String name, Pageable pageable) {
        return foodRepository.findAllByNameContaining(name, pageable);
    }


    @Override
    public List<Food> findMaxPrice() {
        return foodRepository.findMaxPrice();
    }


    @Override
    public List<Food> findMinPrice() {
        return foodRepository.findMinPrice();
    }

    @Override
    public Page<Food> findFoodByPriceBetween(double price1, double price2, Pageable pageable) {
        return foodRepository.findFoodByPriceBetween(price1, price2, pageable);
    }


    @Override
    public Page<Food> findFoodByCategoryContaining(String name, Pageable pageable) {
        return foodRepository.findFoodByCategoryContaining("%" + name + "%", pageable);
    }


    @Override
    public Page<Food> findFoodByAvgPrice(Pageable pageable) {
        return foodRepository.findFoodByAvgPrice(pageable);
    }

}
