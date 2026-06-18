package com.cakies.controller;

import com.cakies.dto.Dtos.*;
import com.cakies.service.CartService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.UUID;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    private String resolveSessionId(String sessionId) {
        if (sessionId == null || sessionId.isEmpty() || sessionId.equals("null")) {
            return UUID.randomUUID().toString();
        }
        return sessionId;
    }

    @GetMapping
    public CartResponse getCart(@RequestHeader(value = "X-Session-Id", required = false) String sessionId) {
        return cartService.getOrCreateCart(resolveSessionId(sessionId));
    }

    @PostMapping
    public CartResponse addToCart(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @RequestBody CartItemRequest request) {
        return cartService.addToCart(resolveSessionId(sessionId), request);
    }

    @PutMapping("/{itemId}")
    public CartResponse updateCartItem(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @PathVariable Long itemId,
            @RequestBody CartItemRequest request) {
        return cartService.updateCartItem(resolveSessionId(sessionId), itemId, request.getQuantity());
    }

    @DeleteMapping("/{itemId}")
    public CartResponse removeCartItem(
            @RequestHeader(value = "X-Session-Id", required = false) String sessionId,
            @PathVariable Long itemId) {
        return cartService.removeCartItem(resolveSessionId(sessionId), itemId);
    }
}
