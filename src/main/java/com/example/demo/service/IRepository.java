package com.example.demo.service;

import java.util.List;
import java.util.Optional;

public interface IRepository<T, F> {
    Optional<F> findById(T id);

    List<F> findAll();

    F save(F entity);
}
