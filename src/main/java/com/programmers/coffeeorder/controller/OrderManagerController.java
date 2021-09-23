package com.programmers.coffeeorder.controller;

import com.programmers.coffeeorder.controller.bind.CoffeeOrderUpdate;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.entity.order.OrderStatus;
import com.programmers.coffeeorder.entity.order.item.ProductOrderItem;
import com.programmers.coffeeorder.service.order.CoffeeOrderService;
import com.programmers.coffeeorder.service.product.CoffeeProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/order")
@RequiredArgsConstructor
public class OrderManagerController {

    private final CoffeeOrderService coffeeOrderService;
    private final CoffeeProductService coffeeProductService;

    private static final String ORDER_READ_TEMPLATE = "manage/order-status";
    private static final String ORDER_LIST_TEMPLATE = "manage/orders";
    private static final String ORDER_UPDATE_TEMPLATE = "manage/update-order";
    private static final String ERROR_MESSAGE_COMPONENT = "error";


    private static String redirectToOrderRead(Long id) {
        return "redirect:/order" + (id == null ? "" : String.format("?id=%d", id));
    }
    private static String redirectToOrderList(Long id) {
        return "redirect:/order/list" + (id == null ? "" : String.format("#order-%d", id));
    }

    @GetMapping
    public String getCoffeeOrder(@RequestParam(name = "id", required = false) Long id,
                                 Model model) {
        model.addAttribute("id", id);
        if(id != null) coffeeOrderService.readOrder(id).ifPresentOrElse(
                order -> model.addAttribute("order", order),
                () -> model.addAttribute(ERROR_MESSAGE_COMPONENT, "Order with given id not found."));
        return ORDER_READ_TEMPLATE;
    }

    @GetMapping("/list")
    // https://javacan.tistory.com/468 use custom resolver?
    public String getCoffeeOrders(@RequestParam(value = "from", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from, // https://www.baeldung.com/spring-date-parameters
                                  @RequestParam(value = "to", required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                                  Model model) {
        if (from == null) from = LocalDateTime.of(1970, 1, 1, 0, 0);
        if (to == null) to = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        List<CoffeeOrder.DTO> orders = coffeeOrderService.listOrdersBetweenTime(from, to);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("orders", orders);
        return ORDER_LIST_TEMPLATE;
    }

    @GetMapping("/accept")
    public String acceptCoffeeOrder(@RequestParam("id") Long id,
                                    @RequestParam(value = "view", required = false, defaultValue = "list") String view) {
        if (id != null) coffeeOrderService.acceptOrder(id);
        return view.equals("list") ? redirectToOrderList(id) : redirectToOrderRead(id);
    }

    @GetMapping("/cancel")
    public String cancelCoffeeOrder(@RequestParam(name = "id") Long id,
                                    @RequestParam(value = "view", required = false, defaultValue = "list") String view) {
        if (id != null) coffeeOrderService.cancelOrder(id);
        return view.equals("list") ? redirectToOrderList(id) : redirectToOrderRead(id);
    }

    @GetMapping("/update")
    public String requestUpdateCoffeeOrder(@RequestParam(value = "id", required = false) Long id,
                                           Model model) {
        model.addAttribute("id", id);
        if (id != null) {
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
                    () -> model.addAttribute(ERROR_MESSAGE_COMPONENT, "NO ORDER FOUND."));
        }
        return ORDER_UPDATE_TEMPLATE;
    }

    @PostMapping("/update")
    public String submitUpdateCoffeeOrder(CoffeeOrderUpdate update) {
        CoffeeOrder updatedCoffeeOrder = new CoffeeOrder(update);
        coffeeOrderService.updateOrderInfo(update.getId(), updatedCoffeeOrder);
        coffeeOrderService.updateOrderItemsQuantity(update.getId(), update.getOrderItems());
        return redirectToOrderRead(update.getId());
    }

}
