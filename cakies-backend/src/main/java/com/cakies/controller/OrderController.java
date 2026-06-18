package com.cakies.controller;

import com.cakies.dto.Dtos.OrderRequest;
import com.cakies.entity.Order;
import com.cakies.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public Order createOrder(
            @RequestHeader(value = "X-Session-Id", required = true) String sessionId,
            @RequestBody OrderRequest request) {
        return orderService.createOrder(sessionId, request);
    }

    @GetMapping("/{id}")
    public Order getOrder(@PathVariable Long id) {
        return orderService.getOrder(id);
    }
}
