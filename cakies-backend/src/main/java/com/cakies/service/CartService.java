package com.cakies.service;

import com.cakies.dto.Dtos.*;
import com.cakies.entity.Cart;
import com.cakies.entity.CartItem;
import com.cakies.entity.Product;
import com.cakies.repository.CartRepository;
import com.cakies.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;

    @Transactional
    public CartResponse getOrCreateCart(String sessionId) {
        Cart cart = getCartEntity(sessionId);
        return mapToCartResponse(cart);
    }

    public Cart getCartEntity(String sessionId) {
        if (sessionId == null || sessionId.isEmpty()) {
            throw new IllegalArgumentException("Session ID is required");
        }
        return cartRepository.findBySessionId(sessionId).orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setSessionId(sessionId);
            return cartRepository.save(newCart);
        });
    }

    @Transactional
    public CartResponse addToCart(String sessionId, CartItemRequest request) {
        Cart cart = getCartEntity(sessionId);
        Long productId = request.getProductId();
        if (productId == null) {
            throw new IllegalArgumentException("Product ID is required");
        }
        Product product = productRepository.findById(productId)
            .orElseThrow(() -> new RuntimeException("Product not found"));

        CartItem existingItem = cart.getCartItems().stream()
                .filter(item -> item.getProduct().getId().equals(product.getId()))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + request.getQuantity());
        } else {
            CartItem newItem = new CartItem();
            newItem.setCart(cart);
            newItem.setProduct(product);
            newItem.setQuantity(request.getQuantity());
            cart.getCartItems().add(newItem);
        }

        cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Transactional
    public CartResponse updateCartItem(String sessionId, Long itemId, Integer quantity) {
        Cart cart = getCartEntity(sessionId);
        CartItem item = cart.getCartItems().stream()
                .filter(i -> i.getId().equals(itemId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("CartItem not found"));
        
        if (quantity <= 0) {
            cart.getCartItems().remove(item);
        } else {
            item.setQuantity(quantity);
        }
        
        cartRepository.save(cart);
        return mapToCartResponse(cart);
    }

    @Transactional
    public CartResponse removeCartItem(String sessionId, Long itemId) {
        return updateCartItem(sessionId, itemId, 0);
    }

    private CartResponse mapToCartResponse(Cart cart) {
        CartResponse response = new CartResponse();
        response.setId(cart.getId());
        response.setSessionId(cart.getSessionId());

        List<CartItemResponse> items = cart.getCartItems().stream().map(item -> {
            CartItemResponse res = new CartItemResponse();
            res.setId(item.getId());
            res.setProductId(item.getProduct().getId());
            res.setProductName(item.getProduct().getName());
            res.setImageUrl(item.getProduct().getImageUrl());
            res.setPrice(item.getProduct().getPrice());
            res.setQuantity(item.getQuantity());
            return res;
        }).collect(Collectors.toList());

        response.setItems(items);

        BigDecimal total = items.stream()
            .map(item -> item.getPrice().multiply(new BigDecimal(item.getQuantity())))
            .reduce(BigDecimal.ZERO, (a, b) -> Objects.requireNonNull(a).add(Objects.requireNonNull(b)));
        response.setTotal(total);

        return response;
    }
}
