package com.programmers.coffeeorder.controller;

import com.programmers.coffeeorder.controller.bind.CoffeeOrderUpdate;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.OrderStatus;
import com.programmers.coffeeorder.entity.order.item.ProductOrderItem;
import com.programmers.coffeeorder.service.delivery.CoffeeDeliveryService;
import com.programmers.coffeeorder.service.order.CoffeeOrderService;
import com.programmers.coffeeorder.service.product.CoffeeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderManagerController {

    private final CoffeeOrderService coffeeOrderService;
    private final CoffeeDeliveryService coffeeDeliveryService;
    private final CoffeeProductService coffeeProductService;

    private static final String REDIRECT_TO_ORDER_LIST = "redirect:/order";

    @GetMapping
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

    @GetMapping("/accept")
    public String acceptCoffeeOrder(@RequestParam("id") Long id) {
        if (id != null) coffeeOrderService.acceptOrder(id);
        return REDIRECT_TO_ORDER_LIST + String.format("#order-%d", id);
    }

    @GetMapping("/cancel")
    public String cancelCoffeeOrder(@RequestParam(name = "id") Long id) {
        if (id != null) coffeeOrderService.cancelOrder(id);
        return REDIRECT_TO_ORDER_LIST + String.format("#order-%d", id);
    }

    @GetMapping("/update")
    public String requestUpdateCoffeeOrder(@RequestParam(value = "id", required = false) Long id,
                                           Model model) {
        model.addAttribute("id", id);
        if (id == null) return "manage/update-order";

        coffeeOrderService.readOrder(id).ifPresentOrElse(
                order -> {
                    model.addAttribute("order", order);
                    model.addAttribute("orderStatuses", OrderStatus.values());

                    Map<Long, Integer> coffeeCount = order.getOrderItems().stream()
                            .collect(Collectors.toMap(
                                    item -> item.getProduct().getProductId(),
                                    ProductOrderItem.DTO::getQuantity));
                    model.addAttribute("coffeeCount", coffeeCount);

                    model.addAttribute("coffeeProducts", coffeeProductService.listCoffeeProductsOnSale());
                },
                () -> model.addAttribute("error", "NO ORDER FOUND."));
        return "manage/update-order";
    }

    @PostMapping("/update")
    public String submitUpdateCoffeeOrder(CoffeeOrderUpdate update) {
        CoffeeOrder updatedCoffeeOrder = new CoffeeOrder(update);
        coffeeOrderService.updateOrderInfo(update.getId(), updatedCoffeeOrder);
        coffeeOrderService.updateOrderItemsQuantity(update.getId(), update.getOrderItems());
        return "redirect:/order/update?id=" + update.getId();
    }

}
