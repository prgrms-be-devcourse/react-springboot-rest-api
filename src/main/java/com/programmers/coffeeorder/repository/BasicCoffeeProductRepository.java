package com.programmers.coffeeorder.repository;

import com.programmers.coffeeorder.entity.CoffeeProduct;
import com.programmers.coffeeorder.entity.CoffeeType;

import java.util.Arrays;
import java.util.List;

public class BasicCoffeeProductRepository implements CoffeeProductRepository {
    @Override
    public List<CoffeeProduct> listCoffeeProducts() {
        // for test!
        return Arrays.asList(
                new CoffeeProduct(1L, "Basic Latte", CoffeeType.LATTE, 2500, "Our coffee shop's basic latte for everyone."),
                new CoffeeProduct(2L, "Kafu Chino", CoffeeType.CAPPUCCINO, 5000, "Premium cappuccino for special customer."),
                new CoffeeProduct(3L, "Caramel Macchiato", CoffeeType.MACCHIATO, 3500, "Macchiato with sweet caramel."),
                new CoffeeProduct(4L, "Black Coffee", CoffeeType.BLACK, 3000, "Deep dark black coffee for adult."));
    }
}
