package com.example.bai_tap_co_ban_2.service;

import com.example.bai_tap_co_ban_2.common.ICRUDService;
import com.example.bai_tap_co_ban_2.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface IServiceCategory extends ICRUDService<Category> {
    Page<Category> findAllByNameContaining (String name, Pageable pageable);

}
