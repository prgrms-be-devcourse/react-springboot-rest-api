package com.programmers.coffeeorder.controller;

import com.programmers.coffeeorder.controller.bind.CoffeeOrderDeliveryRequest;
import com.programmers.coffeeorder.entity.delivery.CoffeeOrderDelivery;
import com.programmers.coffeeorder.entity.delivery.DeliveryStatus;
import com.programmers.coffeeorder.service.delivery.CoffeeDeliveryService;
import com.programmers.coffeeorder.service.order.CoffeeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;

@Controller
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final CoffeeOrderService coffeeOrderService;
    private final CoffeeDeliveryService coffeeDeliveryService;

    private static final String DELIVERY_REQUEST_TEMPLATE = "deliveries/delivery-request";
    private static final String DELIVERY_READ_TEMPLATE = "deliveries/delivery-status";
    private static final String ERROR_MESSAGE_COMPONENT = "error";


    @GetMapping
    public String readDeliveryInfo(@RequestParam("id") long id,
                                   Model model) {
        model.addAttribute("id", id);
        coffeeDeliveryService.readCoffeeOrderDelivery(id).ifPresentOrElse(
                delivery -> model.addAttribute("delivery", delivery),
                () -> model.addAttribute(ERROR_MESSAGE_COMPONENT, "Delivery with given id not found."));
        return DELIVERY_READ_TEMPLATE;
    }

    @GetMapping("/create")
    public String requestDeliveryCreate(@RequestParam("id") long id,
                                        Model model) {
        model.addAttribute("id", id);
        coffeeOrderService.readOrder(id).ifPresentOrElse(
                order -> model.addAttribute("order", order),
                () -> model.addAttribute(ERROR_MESSAGE_COMPONENT, "Order with given id not found."));
        return DELIVERY_REQUEST_TEMPLATE;
    }

    @PostMapping("/create")
    public String submitDeliveryCreate(CoffeeOrderDeliveryRequest request,
                                       Model model) {
        model.addAttribute("request", request);
        model.addAttribute("id", request.getOrderId());
        if (request.getOrderId() == null) {
            model.addAttribute(ERROR_MESSAGE_COMPONENT, "orderId cannot be null.");
            return DELIVERY_REQUEST_TEMPLATE;
        }

        try {
            CoffeeOrderDelivery.DTO coffeeOrder = coffeeDeliveryService.createCoffeeOrderDelivery(
                    request.getOrderId(),
                    CoffeeOrderDelivery.builder()
                            .sender(request.getSender())
                            .receiver(request.getReceiver())
                            .destination(request.getDestination())
                            .message(request.getMessage())
                            .deliveryStatus(DeliveryStatus.NOT_DELIVERED)
                            .createdAt(LocalDateTime.now())
                            .updatedAt(LocalDateTime.now())
                            .build());
            return "redirect:/delivery?id=" + coffeeOrder.getId();
        } catch (RuntimeException ex) {
            coffeeOrderService.readOrder(request.getOrderId()).ifPresent(order -> model.addAttribute("order", order));
            model.addAttribute(ERROR_MESSAGE_COMPONENT, ex.getMessage());
            return DELIVERY_REQUEST_TEMPLATE;
        }
    }

}
