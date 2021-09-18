package com.programmers.coffeeorder.service.product;

import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;

import java.util.List;

public interface CoffeeProductService {
    List<CoffeeProduct.DTO> listCoffeeProductsOnSale();
}
