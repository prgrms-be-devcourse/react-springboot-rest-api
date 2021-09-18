package com.programmers.coffeeorder.controller;

import com.programmers.coffeeorder.entity.CoffeeOrder;
import com.programmers.coffeeorder.service.order.CoffeeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManagerController {

    private final CoffeeOrderService coffeeOrderService;

    @GetMapping("/orders")
    // TODO: https://javacan.tistory.com/468 use custom resolver?
    public String getCoffeeOrders(@RequestParam(value = "from", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from, // https://www.baeldung.com/spring-date-parameters
                                  @RequestParam(value = "to", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                                  Model model) {
        if (from == null) from = LocalDateTime.of(1970, 1, 1, 0, 0);
        if (to == null) to = LocalDateTime.now();
        List<CoffeeOrder.DTO> orders = coffeeOrderService.listOrdersBetweenTime(from, to);
        model.addAttribute("orders", orders);
        return "manage/orders";
    }

}
