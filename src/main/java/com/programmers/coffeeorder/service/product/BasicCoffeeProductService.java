package com.programmers.coffeeorder.service.product;

import com.programmers.coffeeorder.entity.product.coffee.CoffeeProduct;
import com.programmers.coffeeorder.repository.product.CoffeeProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class BasicCoffeeProductService implements CoffeeProductService {

    private final CoffeeProductRepository coffeeProductRepository;

    @Override
    @Transactional(readOnly = true)
    public List<CoffeeProduct.DTO> listCoffeeProductsOnSale() {
        return coffeeProductRepository.listCoffeeProducts().stream()
                .map(CoffeeProduct::toDTO)
                .collect(Collectors.toList());
    }

}
