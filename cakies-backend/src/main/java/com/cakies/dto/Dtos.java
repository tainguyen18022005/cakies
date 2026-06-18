package com.cakies.dto;

import lombok.Data;
import java.math.BigDecimal;
import java.util.List;

public class Dtos {

    @Data
    public static class CartItemRequest {
        private Long productId;
        private Integer quantity;
    }

    @Data
    public static class CartItemResponse {
        private Long id;
        private Long productId;
        private String productName;
        private String imageUrl;
        private BigDecimal price;
        private Integer quantity;
    }

    @Data
    public static class CartResponse {
        private Long id;
        private String sessionId;
        private List<CartItemResponse> items;
        private BigDecimal total;
    }

    @Data
    public static class OrderRequest {
        private String customerName;
        private String customerPhone;
        private String customerAddress;
    }
}
