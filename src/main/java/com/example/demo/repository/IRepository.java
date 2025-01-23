package com.example.demo.repository;

import java.util.List;
import java.util.Optional;

public interface IRepository<T, ID> {
    Optional<T> findById(ID id);

    List<T> findAll();

    T save(T entity);

    void delete(ID id);
}
