package com.example.bai_tap_co_ban_2.repository;

import com.example.bai_tap_co_ban_2.model.Category;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.stereotype.Repository;

@Repository
@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Long> {

    Page<Category> findAllByNameContaining (String name, Pageable pageable);
}
