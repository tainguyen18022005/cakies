<template>
  <div class="container my-5">
    <div class="checkout-header text-center mb-5">
      <h1 class="page-title">Thanh Toán</h1>
      <div class="wavy-divider mx-auto mt-3" style="max-width: 200px; margin-bottom: 0; opacity: 0.6;"></div>
    </div>

    <div class="checkout-content">
      <!-- Form Info -->
      <div class="checkout-form-section">
        <h2 class="section-title mb-4">Thông tin giao hàng</h2>
        
        <form @submit.prevent="submitOrder" class="checkout-form">
          <div class="form-group">
            <label for="name">Họ và tên</label>
            <input type="text" id="name" v-model="form.customerName" class="form-control" required placeholder="Ví dụ: Nguyễn Văn A" />
          </div>
          
          <div class="form-group">
            <label for="phone">Số điện thoại</label>
            <input type="tel" id="phone" v-model="form.customerPhone" class="form-control" required placeholder="Ví dụ: 0901234567" />
          </div>
          
          <div class="form-group">
            <label for="address">Địa chỉ giao hàng</label>
            <textarea id="address" v-model="form.customerAddress" class="form-control" rows="3" required placeholder="Số nhà, tên đường, phường/xã, quận/huyện..."></textarea>
          </div>
          
          <button type="submit" class="btn submit-btn mt-4" :disabled="submitting">
            {{ submitting ? 'Đang xử lý...' : 'Xác Nhận Đặt Hàng' }}
          </button>
        </form>
      </div>

      <!-- Order Summary -->
      <div class="order-summary-section">
        <div class="order-summary">
          <h3 class="summary-title">Đơn hàng của bạn</h3>
          <div class="summary-divider"></div>
          
          <div class="summary-items">
            <div v-for="item in cartStore.items" :key="item.id" class="summary-item">
              <div class="item-info">
                <span class="item-name">{{ item.productName }}</span>
                <span class="item-qty">Số lượng: {{ item.quantity }}</span>
              </div>
              <div class="item-price">
                {{ formatPrice(item.price * item.quantity) }}
              </div>
            </div>
          </div>
          
          <div class="summary-divider mt-4 mb-4"></div>
          
          <div class="summary-row total-row">
            <span class="summary-label">Tổng thanh toán</span>
            <span class="summary-total-price">{{ formatPrice(cartStore.total) }}</span>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { useCartStore } from '../stores/cart';
import api from '../services/api';

const router = useRouter();
const cartStore = useCartStore();

const form = ref({
  customerName: '',
  customerPhone: '',
  customerAddress: ''
});

const submitting = ref(false);

onMounted(() => {
  if (cartStore.items.length === 0 && !cartStore.loading) {
    router.push('/cart');
  }
});

const submitOrder = async () => {
  submitting.value = true;
  try {
    const res = await api.post('/orders', form.value);
    
    // Đặt hàng thành công, xoá giỏ hàng local
    cartStore.clearCart();
    
    // Chuyển hướng tới trang thành công
    router.push(`/success/${res.data.id}`);
  } catch (error) {
    console.error("Lỗi khi tạo đơn hàng:", error);
    alert("Có lỗi xảy ra khi đặt hàng. Vui lòng thử lại!");
  } finally {
    submitting.value = false;
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

.checkout-content {
  display: flex;
  gap: 60px;
  align-items: flex-start;
}

.checkout-form-section {
  flex: 2;
}

.section-title {
  font-family: var(--font-heading);
  font-size: 24px;
  color: var(--text-main);
}

.checkout-form {
  padding-right: 20px;
}

.order-summary-section {
  flex: 1;
  position: sticky;
  top: 100px;
}

.order-summary {
  background-color: transparent;
  padding: 35px;
  border: 1px solid var(--border-color);
  border-radius: 8px;
}

.summary-title {
  font-family: var(--font-heading);
  font-size: 22px;
  color: var(--text-main);
  text-align: center;
}

.summary-divider {
  height: 1px;
  background-color: var(--border-color);
  margin: 20px 0;
}

.summary-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 15px;
  padding-bottom: 15px;
  border-bottom: 1px dashed var(--border-color);
}

.summary-item:last-child {
  border-bottom: none;
  margin-bottom: 0;
  padding-bottom: 0;
}

.item-info {
  display: flex;
  flex-direction: column;
}

.item-name {
  font-family: var(--font-body);
  font-size: 15px;
  color: var(--text-main);
  font-weight: 500;
  margin-bottom: 4px;
}

.item-qty {
  font-family: var(--font-body);
  font-size: 13px;
  color: var(--text-main);
  opacity: 0.6;
}

.item-price {
  font-family: var(--font-body);
  font-size: 15px;
  color: var(--text-main);
  font-variant-numeric: tabular-nums;
}

.summary-row {
  display: flex;
  justify-content: space-between;
  align-items: center;
  font-family: var(--font-body);
}

.total-row {
  align-items: flex-end;
}

.summary-label {
  font-size: 16px;
  font-weight: 600;
  color: var(--text-main);
}

.summary-total-price {
  color: var(--accent-main);
  font-size: 24px;
  font-weight: 600;
  font-variant-numeric: tabular-nums;
}

.submit-btn {
  width: 100%;
  padding: 16px;
  font-size: 16px;
}

@media (max-width: 992px) {
  .checkout-content {
    flex-direction: column;
    gap: 40px;
  }
  .checkout-form {
    padding-right: 0;
  }
  .order-summary-section {
    position: static;
    width: 100%;
  }
}
</style>
