<template>
  <div class="success-container text-center">
    <div class="success-card">
      <div class="success-icon mb-4">✅</div>
      <h1 class="mb-2">Đặt hàng thành công!</h1>
      <p class="text-light mb-4">Cảm ơn bạn đã mua sắm tại Cakies.</p>
      
      <div v-if="loading">Đang tải thông tin đơn hàng...</div>
      
      <div v-else-if="order" class="order-details">
        <p>Mã đơn hàng của bạn là:</p>
        <h2 class="order-id">#{{ order.id }}</h2>
        
        <div class="order-info mt-4">
          <p><strong>Người nhận:</strong> {{ order.customerName }}</p>
          <p><strong>Số điện thoại:</strong> {{ order.customerPhone }}</p>
          <p><strong>Địa chỉ:</strong> {{ order.customerAddress }}</p>
          <p><strong>Tổng tiền:</strong> <span class="total-price">{{ formatPrice(order.totalAmount) }}</span></p>
        </div>
      </div>
      
      <router-link to="/" class="btn mt-4">Trở về trang chủ</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute } from 'vue-router';
import api from '../services/api';

const route = useRoute();
const order = ref(null);
const loading = ref(true);

onMounted(async () => {
  try {
    const res = await api.get(`/orders/${route.params.orderId}`);
    order.value = res.data;
  } catch (error) {
    console.error("Lỗi tải thông tin đơn hàng:", error);
  } finally {
    loading.value = false;
  }
});

const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
};
</script>

<style scoped>
.success-container {
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 60vh;
}

.success-card {
  background-color: var(--bg-color);
  padding: 40px;
  border-radius: 8px;
  box-shadow: 0 4px 6px rgba(0,0,0,0.05);
  max-width: 500px;
  width: 100%;
}

.success-icon {
  font-size: 60px;
}

.order-id {
  color: var(--primary-color);
  font-size: 32px;
  margin-top: 10px;
}

.order-info {
  text-align: left;
  background-color: var(--secondary-color);
  padding: 20px;
  border-radius: 8px;
}

.order-info p {
  margin-bottom: 10px;
}

.total-price {
  color: var(--primary-color);
  font-weight: bold;
  font-size: 18px;
}
</style>
