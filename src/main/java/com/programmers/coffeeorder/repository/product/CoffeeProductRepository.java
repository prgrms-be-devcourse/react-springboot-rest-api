package com.programmers.coffeeorder.repository.product;

import com.programmers.coffeeorder.entity.CoffeeProduct;

import java.util.List;

public interface CoffeeProductRepository {
    List<CoffeeProduct> listCoffeeProducts();
}
