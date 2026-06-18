<template>
  <div class="container my-5">
    <div class="cart-header text-center mb-5">
      <h1 class="page-title">Giỏ Hàng</h1>
      <div class="wavy-divider mx-auto mt-3" style="max-width: 200px; margin-bottom: 0; opacity: 0.6;"></div>
    </div>

    <div v-if="cartStore.loading" class="text-center py-5">
      <p>Đang tải giỏ hàng...</p>
    </div>

    <div v-else-if="cartStore.items.length === 0" class="empty-cart text-center py-5">
      <p class="mb-4">Giỏ hàng của bạn đang trống.</p>
      <router-link to="/" class="btn">Tiếp tục mua sắm</router-link>
    </div>

    <div v-else class="cart-content">
      <div class="cart-items-section">
        <div class="cart-items-header">
          <span class="header-product">Sản phẩm</span>
          <span class="header-qty text-center">Số lượng</span>
          <span class="header-total text-right">Tổng</span>
        </div>
        
        <div class="cart-items-list">
          <div v-for="item in cartStore.items" :key="item.id" class="cart-item">
            <div class="item-product">
              <div class="item-image-wrapper">
                <img :src="item.imageUrl" :alt="item.productName" class="cart-item-img" />
              </div>
              <div class="item-info">
                <h3 class="item-name">{{ item.productName }}</h3>
                <p class="item-price">{{ formatPrice(item.price) }}</p>
                <button @click="removeItem(item.id)" class="btn-remove mt-2">Xoá</button>
              </div>
            </div>
            
            <div class="item-qty text-center">
              <div class="qty-controls">
                <button @click="updateQty(item.id, item.quantity - 1)" class="btn-qty">-</button>
                <span class="qty-display">{{ item.quantity }}</span>
                <button @click="updateQty(item.id, item.quantity + 1)" class="btn-qty">+</button>
              </div>
            </div>
            
            <div class="item-total-col text-right">
              <p class="item-total">{{ formatPrice(item.price * item.quantity) }}</p>
            </div>
          </div>
        </div>
      </div>

      <div class="cart-summary-section">
        <div class="cart-summary">
          <h3 class="summary-title">Tóm tắt đơn hàng</h3>
          <div class="summary-divider"></div>
          
          <div class="summary-row">
            <span class="summary-label">Tạm tính</span>
            <span class="summary-value">{{ formatPrice(cartStore.total) }}</span>
          </div>
          
          <div class="summary-row mt-3">
            <span class="summary-label">Phí giao hàng</span>
            <span class="summary-value">Miễn phí</span>
          </div>
          
          <div class="summary-divider mt-4 mb-4"></div>
          
          <div class="summary-row total-row">
            <span class="summary-label">Tổng cộng</span>
            <span class="summary-total-price">{{ formatPrice(cartStore.total) }}</span>
          </div>
          
          <router-link to="/checkout" class="btn checkout-btn mt-5">
            Tiến hành thanh toán
          </router-link>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useCartStore } from '../stores/cart';

const cartStore = useCartStore();

const updateQty = async (itemId, newQty) => {
  if (newQty > 0) {
    await cartStore.updateQuantity(itemId, newQty);
  }
};

const removeItem = async (itemId) => {
  if(confirm("Bạn có chắc muốn xoá sản phẩm này khỏi giỏ hàng?")) {
    await cartStore.removeItem(itemId);
  }
};

const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
};
</script>

<style scoped>
.page-title {
  font-size: 36px;
  color: var(--text-main);
}

.cart-content {
  display: flex;
  gap: 60px;
  align-items: flex-start;
}

.cart-items-section {
  flex: 2;
}

.cart-items-header {
  display: grid;
  grid-template-columns: 3fr 1fr 1fr;
  padding-bottom: 15px;
  border-bottom: 1px dashed var(--border-color);
  margin-bottom: 25px;
  font-family: var(--font-body);
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: 600;
  color: var(--text-main);
  opacity: 0.7;
}

.cart-items-list {
  display: flex;
  flex-direction: column;
  gap: 30px;
}

.cart-item {
  display: grid;
  grid-template-columns: 3fr 1fr 1fr;
  align-items: center;
  padding-bottom: 30px;
  border-bottom: 1px dashed var(--border-color);
}

.item-product {
  display: flex;
  gap: 20px;
  align-items: center;
}

.item-image-wrapper {
  width: 90px;
  height: 110px;
  flex-shrink: 0;
  border-radius: 6px;
  overflow: hidden;
  background-color: var(--border-color);
}

.cart-item-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
}

.item-info {
  display: flex;
  flex-direction: column;
  justify-content: center;
}

.item-name {
  font-family: var(--font-heading);
  font-size: 20px;
  color: var(--text-main);
  margin-bottom: 6px;
}

.item-price {
  font-family: var(--font-body);
  font-size: 15px;
  color: var(--accent-sub);
  font-variant-numeric: tabular-nums;
}

.btn-remove {
  background: none;
  border: none;
  color: var(--text-main);
  opacity: 0.5;
  cursor: pointer;
  font-family: var(--font-body);
  font-size: 13px;
  text-align: left;
  padding: 0;
  transition: opacity 0.2s, color 0.2s;
  text-transform: uppercase;
  letter-spacing: 0.5px;
}

.btn-remove:hover {
  opacity: 1;
  color: var(--accent-main);
  text-decoration: underline;
}

.qty-controls {
  display: inline-flex;
  align-items: center;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  overflow: hidden;
}

.btn-qty {
  width: 35px;
  height: 35px;
  background-color: transparent;
  border: none;
  cursor: pointer;
  font-size: 16px;
  color: var(--text-main);
  transition: background-color 0.2s;
}

.btn-qty:hover {
  background-color: var(--border-color);
}

.qty-display {
  width: 40px;
  text-align: center;
  font-family: var(--font-body);
  font-size: 15px;
  color: var(--text-main);
}

.item-total {
  font-family: var(--font-body);
  font-weight: 500;
  color: var(--text-main);
  font-size: 16px;
  font-variant-numeric: tabular-nums;
}

.cart-summary-section {
  flex: 1;
  position: sticky;
  top: 100px;
}

.cart-summary {
  background-color: transparent;
  padding: 35px;
  border: 1px solid var(--border-color);
  border-radius: 8px;
}

.summary-title {
  font-size: 22px;
  color: var(--text-main);
  margin-bottom: 20px;
  text-align: center;
}

.summary-divider {
  height: 1px;
  background-color: var(--border-color);
  margin: 20px 0;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-family: var(--font-body);
}

.summary-label {
  color: var(--text-main);
  opacity: 0.8;
  font-size: 15px;
}

.summary-value {
  color: var(--text-main);
  font-weight: 500;
  font-size: 15px;
  font-variant-numeric: tabular-nums;
}

.total-row {
  align-items: flex-end;
}

.total-row .summary-label {
  font-size: 16px;
  font-weight: 600;
  opacity: 1;
}

.summary-total-price {
  color: var(--accent-main);
  font-size: 24px;
  font-weight: 600;
  font-variant-numeric: tabular-nums;
}

.checkout-btn {
  width: 100%;
}

.text-right {
  text-align: right;
}

.empty-cart p {
  font-family: var(--font-body);
  font-size: 18px;
  color: var(--text-main);
  opacity: 0.8;
}

@media (max-width: 992px) {
  .cart-content {
    flex-direction: column;
  }
  .cart-summary-section {
    position: static;
    width: 100%;
  }
}

@media (max-width: 768px) {
  .cart-items-header {
    display: none;
  }
  .cart-item {
    grid-template-columns: 1fr;
    gap: 20px;
  }
  .item-qty, .item-total-col {
    text-align: left;
  }
}
</style>
