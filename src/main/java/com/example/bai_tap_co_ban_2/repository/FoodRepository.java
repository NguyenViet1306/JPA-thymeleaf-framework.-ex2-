package com.example.bai_tap_co_ban_2.repository;

import com.example.bai_tap_co_ban_2.model.Food;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
@EnableJpaRepositories
public interface FoodRepository extends JpaRepository<Food, Long> {
    Page<Food> findAllByNameContaining(String name, Pageable pageable);

    @Query(value = "select * from food where price = (select max(price) from food)",nativeQuery = true )
    List<Food> findMaxPrice( );

    @Query(value = "select * from food where price = (select min(price) from food)",nativeQuery = true )
    List<Food> findMinPrice( );

    Page<Food> findFoodByPriceBetween(double price1, double price2, Pageable pageable);

    @Query(value = "select * from food where category_id = (select idC from (select avg(price) as tb, food.category_id as idC from food group by category_id) as tableNew where tb =\n" +
            "(select max(tb) from (select avg(price) as tb, food.category_id as idC from food group by category_id) as tableNew))",nativeQuery = true)
    Page<Food> findFoodByAvgPrice(Pageable pageable);


    @Query(value = "select * from food join category on category.id = food.category_id where category.name like :name", nativeQuery = true)
    Page<Food> findFoodByCategoryContaining (@Param("name") String name, Pageable pageable);

}
