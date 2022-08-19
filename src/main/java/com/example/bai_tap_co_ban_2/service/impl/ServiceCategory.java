package com.example.bai_tap_co_ban_2.service.impl;

import com.example.bai_tap_co_ban_2.model.Category;
import com.example.bai_tap_co_ban_2.repository.CategoryRepository;
import com.example.bai_tap_co_ban_2.service.IServiceCategory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public class ServiceCategory implements IServiceCategory {
    @Autowired
    private CategoryRepository categoryRepository;

    @Override
    public Page<Category> findAll(Pageable pageable) {
        return categoryRepository.findAll(pageable);
    }

    @Override
    public Category save(Category category) {
        return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return categoryRepository.findById(id);
    }


    @Override
    public Page<Category> findAllByNameContaining(String name, Pageable pageable) {
        return categoryRepository.findAllByNameContaining("%" + name + "%",pageable);
    }
}
