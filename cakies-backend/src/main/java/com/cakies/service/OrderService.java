package com.cakies.service;

import com.cakies.dto.Dtos.OrderRequest;
import com.cakies.entity.Cart;
import com.cakies.entity.Order;
import com.cakies.entity.OrderItem;
import com.cakies.repository.CartRepository;
import com.cakies.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final CartService cartService;
    private final CartRepository cartRepository;

    @Transactional
    public Order createOrder(String sessionId, OrderRequest request) {
        Cart cart = cartService.getCartEntity(sessionId);
        
        if (cart.getCartItems().isEmpty()) {
            throw new RuntimeException("Cart is empty");
        }

        Order order = new Order();
        order.setCustomerName(request.getCustomerName());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setCustomerAddress(request.getCustomerAddress());

        BigDecimal totalAmount = BigDecimal.ZERO;

        for (var cartItem : cart.getCartItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setOrder(order);
            orderItem.setProductSnapshotName(cartItem.getProduct().getName());
            orderItem.setPriceAtPurchase(cartItem.getProduct().getPrice());
            orderItem.setQuantity(cartItem.getQuantity());
            order.getOrderItems().add(orderItem);

            totalAmount = totalAmount.add(orderItem.getPriceAtPurchase().multiply(new BigDecimal(orderItem.getQuantity())));
        }

        order.setTotalAmount(totalAmount);
        
        // Save order
        Order savedOrder = orderRepository.save(order);

        // Clear cart
        cart.getCartItems().clear();
        cartRepository.save(cart);

        return savedOrder;
    }

    public Order getOrder(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }
}
