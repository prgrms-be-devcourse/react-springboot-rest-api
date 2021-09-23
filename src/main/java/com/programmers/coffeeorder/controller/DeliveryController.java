package com.programmers.coffeeorder.controller;

import com.programmers.coffeeorder.controller.bind.CoffeeOrderDeliveryRequest;
import com.programmers.coffeeorder.controller.bind.CoffeeOrderDeliveryUpdate;
import com.programmers.coffeeorder.entity.delivery.CoffeeOrderDelivery;
import com.programmers.coffeeorder.entity.delivery.DeliveryStatus;
import com.programmers.coffeeorder.entity.order.CoffeeOrder;
import com.programmers.coffeeorder.service.delivery.CoffeeDeliveryService;
import com.programmers.coffeeorder.service.order.CoffeeOrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Controller
@RequestMapping("/delivery")
@RequiredArgsConstructor
public class DeliveryController {

    private final CoffeeOrderService coffeeOrderService;
    private final CoffeeDeliveryService coffeeDeliveryService;

    private static final String DELIVERY_REQUEST_TEMPLATE = "deliveries/delivery-request";
    private static final String DELIVERY_LIST_TEMPLATE = "deliveries/delivery-list";
    private static final String DELIVERY_READ_TEMPLATE = "deliveries/delivery-status";
    private static final String DELIVERY_UPDATE_TEMPLATE = "deliveries/delivery-update";
    private static final String DELIVERY_SCHEDULED_TEMPLATE = "deliveries/deliveries-scheduled";
    private static final String ERROR_MESSAGE_COMPONENT = "error";
    private static final String DELIVERY_INFO_COMPONENT = "delivery";

    private static final List<String[]> urlMap = new LinkedList<>(Arrays.asList(
            new String[]{"Main Page", "/"},
            new String[]{"Create Delivery", "/delivery/create"},
            new String[]{"Delivery List", "/delivery/list"},
            new String[]{"Inspect Delivery", "/delivery"},
            new String[]{"Update Delivery", "/delivery/update"},
            new String[]{"Scheduled Delivery", "/delivery/scheduled"}
    ));


    private static String redirectToDeliveryRead(Long id) {
        return "redirect:/delivery" + (id == null ? "" : String.format("?id=%d", id));
    }

    @GetMapping
    public String readDeliveryInfo(@RequestParam(value = "id", required = false) Long id,
                                   Model model) {
        model.addAttribute("urls", urlMap);
        model.addAttribute("id", id);
        if (id != null) {
            coffeeDeliveryService.readCoffeeOrderDelivery(id).ifPresentOrElse(
                    delivery -> model.addAttribute(DELIVERY_INFO_COMPONENT, delivery),
                    () -> model.addAttribute(ERROR_MESSAGE_COMPONENT, "Delivery with given id not found."));
        }
        return DELIVERY_READ_TEMPLATE;
    }

    @GetMapping("/update")
    public String requestCoffeeOrderDeliveryUpdate(@RequestParam(value = "id", required = false) Long id,
                                                   Model model) {
        model.addAttribute("urls", urlMap);
        model.addAttribute("id", id);
        if (id != null) {
            coffeeDeliveryService.readCoffeeOrderDelivery(id).ifPresentOrElse(
                    delivery -> model.addAttribute(DELIVERY_INFO_COMPONENT, delivery),
                    () -> model.addAttribute(ERROR_MESSAGE_COMPONENT, "NO DELIVERY FOUND."));
            model.addAttribute("deliveryStatuses", DeliveryStatus.values());
        }
        return DELIVERY_UPDATE_TEMPLATE;
    }

    @PostMapping("/update")
    public String submitCoffeeOrderDeliveryUpdate(CoffeeOrderDeliveryUpdate update,
                                                  Model model) {
        model.addAttribute("urls", urlMap);
        model.addAttribute("id", update.getId());
        try {
            coffeeDeliveryService.updateDelivery(CoffeeOrderDelivery.builder()
                    .id(update.getId())
                    .deliveryStatus(DeliveryStatus.of(update.getDeliveryStatus()))
                    .sender(update.getSender())
                    .receiver(update.getReceiver())
                    .destination(update.getDestination())
                    .message(update.getMessage())
                    .orderId(update.getOrderId()).build());
            return redirectToDeliveryRead(update.getId());
        } catch (RuntimeException exception) {
            model.addAttribute(ERROR_MESSAGE_COMPONENT, exception.getMessage());
            coffeeDeliveryService.readCoffeeOrderDelivery(update.getId()).ifPresentOrElse(
                    delivery -> model.addAttribute(DELIVERY_INFO_COMPONENT, delivery),
                    () -> model.addAttribute(ERROR_MESSAGE_COMPONENT, "NO DELIVERY FOUND."));
            model.addAttribute("deliveryStatuses", DeliveryStatus.values());
            return DELIVERY_UPDATE_TEMPLATE;
        }
    }

    @GetMapping("/list")
    public String listDeliveries(@RequestParam(value = "from", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime from,
                                 @RequestParam(value = "to", required = false)
                                 @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime to,
                                 Model model) {
        model.addAttribute("urls", urlMap);
        if (from == null) from = LocalDateTime.of(1970, 1, 1, 0, 0);
        if (to == null) to = LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        model.addAttribute("from", from);
        model.addAttribute("to", to);
        model.addAttribute("deliveries", coffeeDeliveryService.listCoffeeOrderDeliveries(from, to));
        return DELIVERY_LIST_TEMPLATE;
    }

    @GetMapping("/create")
    public String requestDeliveryCreate(@RequestParam(value = "id", required = false) Long id,
                                        Model model) {
        model.addAttribute("urls", urlMap);
        if (id != null) {
            model.addAttribute("id", id);
            coffeeOrderService.readOrder(id).ifPresentOrElse(
                    order -> model.addAttribute("order", order),
                    () -> model.addAttribute(ERROR_MESSAGE_COMPONENT, "Order with given id not found."));
        }
        return DELIVERY_REQUEST_TEMPLATE;
    }

    @PostMapping("/create")
    public String submitDeliveryCreate(CoffeeOrderDeliveryRequest request,
                                       Model model) {
        model.addAttribute("urls", urlMap);
        model.addAttribute("request", request);
        model.addAttribute("id", request.getOrderId());
        if (request.getOrderId() == null) {
            model.addAttribute(ERROR_MESSAGE_COMPONENT, "orderId cannot be null.");
            return DELIVERY_REQUEST_TEMPLATE;
        }

        try {
            CoffeeOrderDelivery.DTO delivery = coffeeDeliveryService.createCoffeeOrderDelivery(
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
            return redirectToDeliveryRead(delivery.getId());
        } catch (RuntimeException ex) {
            coffeeOrderService.readOrder(request.getOrderId()).ifPresent(order -> model.addAttribute("order", order));
            model.addAttribute(ERROR_MESSAGE_COMPONENT, ex.getMessage());
            return DELIVERY_REQUEST_TEMPLATE;
        }
    }

    @GetMapping("/scheduled")
    public String getCoffeeDeliveryReservations(@RequestParam(value = "date", required = false)
                                                @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date,
                                                Model model) {
        model.addAttribute("urls", urlMap);
        if (date == null) date = LocalDate.now();
        Map<String, List<CoffeeOrder.DTO>> deliveries = coffeeDeliveryService.listAppointedDeliveries(date);
        model.addAttribute("deliveries", deliveries);
        model.addAttribute("date", date);
        return DELIVERY_SCHEDULED_TEMPLATE;
    }
}
