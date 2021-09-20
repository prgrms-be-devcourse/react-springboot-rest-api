package com.programmers.coffeeorder.controller;

import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.service.delivery.CoffeeDeliveryService;
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
import java.util.Map;

@Controller
@RequestMapping("/manage")
@RequiredArgsConstructor
public class ManagerController {

    private final CoffeeOrderService coffeeOrderService;
    private final CoffeeDeliveryService coffeeDeliveryService;

    private static final String REDIRECT_TO_ORDER_LIST = "redirect:/manage/orders";

    @GetMapping("/orders")
    // https://javacan.tistory.com/468 use custom resolver?
    public String getCoffeeOrders(@RequestParam(value = "from", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from, // https://www.baeldung.com/spring-date-parameters
                                  @RequestParam(value = "to", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                                  Model model) {
        if (from == null) from = LocalDateTime.of(1970, 1, 1, 0, 0);
        if (to == null) to = LocalDateTime.now();
        List<CoffeeOrder.DTO> orders = coffeeOrderService.listOrdersBetweenTime(from, to);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("orders", orders);
        return "manage/orders";
    }

    @GetMapping("/orders/accept")
    public String acceptCoffeeOrder(@RequestParam("id") Long id) {
        if(id != null) coffeeOrderService.acceptOrder(id);
        return REDIRECT_TO_ORDER_LIST;
    }

    @GetMapping("/orders/cancel")
    public String cancelCoffeeOrder(@RequestParam(name = "id") Long id) {
        if(id != null) coffeeOrderService.cancelOrder(id);
        return REDIRECT_TO_ORDER_LIST;
    }

    @GetMapping("/deliveries")
    public String getCoffeeDeliveryReservations(@RequestParam(value = "date", required = false)
                                          @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                          Model model) {
        if(date == null) date = LocalDate.now();
        Map<String, List<CoffeeOrder.DTO>> deliveries = coffeeDeliveryService.listAppointedDeliveries(date);
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("date", date);
        return "manage/deliveries-scheduled";
    }

}
