package com.example.bai_tap_co_ban_2.common;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface ICRUDService<E> {
    Page<E> findAll(Pageable pageable);

    E save(E e);

    void delete(Long id);

    Optional<E> findById(Long id);
}
