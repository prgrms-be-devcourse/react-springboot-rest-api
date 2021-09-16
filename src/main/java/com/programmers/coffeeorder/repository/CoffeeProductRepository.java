package com.programmers.coffeeorder.repository;

import com.programmers.coffeeorder.entity.CoffeeProduct;

import java.util.List;

public interface CoffeeProductRepository {
    List<CoffeeProduct> listCoffeeProducts();
}
