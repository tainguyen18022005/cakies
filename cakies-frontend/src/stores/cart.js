import { defineStore } from 'pinia';
import api from '../services/api';

export const useCartStore = defineStore('cart', {
    state: () => ({
        items: [],
        total: 0,
        sessionId: localStorage.getItem('sessionId') || null,
        loading: false,
    }),
    
    getters: {
        itemCount: (state) => state.items.reduce((sum, item) => sum + item.quantity, 0),
    },

    actions: {
        async fetchCart() {
            this.loading = true;
            try {
                const response = await api.get('/cart');
                this.items = response.data.items;
                this.total = response.data.total;
                if (response.data.sessionId) {
                    this.sessionId = response.data.sessionId;
                    localStorage.setItem('sessionId', this.sessionId);
                }
            } catch (error) {
                console.error("Failed to fetch cart:", error);
            } finally {
                this.loading = false;
            }
        },

        async addToCart(productId, quantity = 1) {
            try {
                const response = await api.post('/cart', { productId, quantity });
                this.items = response.data.items;
                this.total = response.data.total;
                if (response.data.sessionId) {
                    this.sessionId = response.data.sessionId;
                    localStorage.setItem('sessionId', this.sessionId);
                }
            } catch (error) {
                console.error("Failed to add to cart:", error);
            }
        },

        async updateQuantity(itemId, quantity) {
            try {
                const response = await api.put(`/cart/${itemId}`, { quantity });
                this.items = response.data.items;
                this.total = response.data.total;
            } catch (error) {
                console.error("Failed to update cart item:", error);
            }
        },

        async removeItem(itemId) {
            try {
                const response = await api.delete(`/cart/${itemId}`);
                this.items = response.data.items;
                this.total = response.data.total;
            } catch (error) {
                console.error("Failed to remove item from cart:", error);
            }
        },

        clearCart() {
            this.items = [];
            this.total = 0;
            // Cố tình không xoá sessionId để giữ session hiện tại cho đơn tiếp theo
        }
    }
});
