<template>
  <div class="container my-5">
    <div v-if="loading" class="text-center py-5">
      <p>Đang tải chi tiết sản phẩm...</p>
    </div>
    
    <div v-else-if="product" class="product-detail-container">
      <div class="product-image-container">
        <img :src="product.imageUrl" :alt="product.name" class="product-img" />
      </div>
      
      <div class="product-info-container">
        <div class="breadcrumbs">
          <router-link to="/">Thực đơn</router-link> 
          <span class="separator">/</span> 
          <span class="current">{{ product.name }}</span>
        </div>
        
        <h1 class="product-title">{{ product.name }}</h1>
        <p class="product-price">{{ formatPrice(product.price) }}</p>
        
        <div class="wavy-divider my-4" style="margin: 30px 0; opacity: 0.6;"></div>
        
        <div class="product-description">
          <p>{{ product.description }}</p>
        </div>
        
        <div class="product-actions mt-5">
          <div class="quantity-wrapper">
            <label class="quantity-label">Số lượng</label>
            <div class="qty-controls">
              <button @click="decreaseQty" class="btn-qty">-</button>
              <input type="number" v-model="quantity" min="1" :max="product.stockQuantity" readonly />
              <button @click="increaseQty" class="btn-qty">+</button>
            </div>
            <div class="stock-badge mt-2" v-if="product.stockQuantity > 0">
              <span class="status-dot in-stock"></span> Còn hàng ({{ product.stockQuantity }})
            </div>
            <div class="stock-badge mt-2" v-else>
              <span class="status-dot out-of-stock"></span> Hết hàng
            </div>
          </div>
          
          <button class="btn btn-add-cart mt-4" @click="addToCart" :disabled="adding || product.stockQuantity === 0">
            {{ adding ? 'Đang thêm...' : 'Thêm Vào Giỏ' }}
          </button>
        </div>
      </div>
    </div>
    
    <div v-else class="text-center py-5">
      <h2>Không tìm thấy sản phẩm!</h2>
      <router-link to="/" class="btn mt-4">Quay lại thực đơn</router-link>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import { useCartStore } from '../stores/cart';
import api from '../services/api';

const route = useRoute();
const router = useRouter();
const cartStore = useCartStore();

const product = ref(null);
const loading = ref(true);
const adding = ref(false);
const quantity = ref(1);

const fetchProduct = async () => {
  try {
    const res = await api.get(`/products/${route.params.id}`);
    product.value = res.data;
  } catch (error) {
    console.error("Lỗi khi lấy chi tiết sản phẩm", error);
  } finally {
    loading.value = false;
  }
};

const increaseQty = () => {
  if (quantity.value < product.value.stockQuantity) {
    quantity.value++;
  }
};

const decreaseQty = () => {
  if (quantity.value > 1) {
    quantity.value--;
  }
};

const addToCart = async () => {
  adding.value = true;
  await cartStore.addToCart(product.value.id, quantity.value);
  adding.value = false;
  router.push('/cart');
};

const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
};

onMounted(() => {
  fetchProduct();
});
</script>

<style scoped>
.product-detail-container {
  display: flex;
  gap: 60px;
  align-items: flex-start;
}

.product-image-container {
  flex: 1;
  max-width: 50%;
}

.product-img {
  width: 100%;
  aspect-ratio: 4 / 5;
  object-fit: cover;
  border-radius: 8px;
}

.product-info-container {
  flex: 1;
  padding: 20px 0;
}

.breadcrumbs {
  font-family: var(--font-body);
  font-size: 13px;
  text-transform: uppercase;
  letter-spacing: 1px;
  margin-bottom: 15px;
}

.breadcrumbs a {
  color: var(--text-main);
  text-decoration: none;
  opacity: 0.6;
  transition: opacity 0.2s;
}

.breadcrumbs a:hover {
  opacity: 1;
}

.breadcrumbs .separator {
  margin: 0 8px;
  opacity: 0.4;
}

.breadcrumbs .current {
  color: var(--accent-main);
  font-weight: 500;
}

.product-title {
  font-size: 42px;
  line-height: 1.2;
  margin-bottom: 15px;
  color: var(--text-main);
}

.product-price {
  font-size: 28px;
  color: var(--accent-sub);
  font-family: var(--font-body);
  font-variant-numeric: tabular-nums;
  font-weight: 500;
}

.product-description {
  font-family: var(--font-body);
  font-size: 16px;
  line-height: 1.8;
  color: var(--text-main);
  opacity: 0.85;
}

.product-actions {
  background-color: transparent;
}

.quantity-label {
  display: block;
  font-family: var(--font-body);
  font-size: 12px;
  text-transform: uppercase;
  letter-spacing: 1px;
  font-weight: 600;
  margin-bottom: 10px;
  color: var(--text-main);
}

.qty-controls {
  display: inline-flex;
  align-items: center;
  border: 1px solid var(--border-color);
  border-radius: 6px;
  overflow: hidden;
}

.btn-qty {
  width: 45px;
  height: 45px;
  background-color: transparent;
  border: none;
  cursor: pointer;
  font-size: 20px;
  color: var(--text-main);
  transition: background-color 0.2s;
}

.btn-qty:hover {
  background-color: var(--border-color);
}

.qty-controls input {
  width: 50px;
  height: 45px;
  text-align: center;
  border: none;
  border-left: 1px solid var(--border-color);
  border-right: 1px solid var(--border-color);
  font-family: var(--font-body);
  font-size: 16px;
  background-color: transparent;
  color: var(--text-main);
  outline: none;
}

.stock-badge {
  font-family: var(--font-body);
  font-size: 13px;
  color: var(--text-main);
  opacity: 0.8;
  display: flex;
  align-items: center;
  gap: 6px;
}

.status-dot {
  display: inline-block;
  width: 8px;
  height: 8px;
  border-radius: 50%;
}

.status-dot.in-stock {
  background-color: var(--success-color);
}

.status-dot.out-of-stock {
  background-color: var(--accent-main);
}

.btn-add-cart {
  width: 100%;
  padding: 16px;
  font-size: 16px;
}

@media (max-width: 768px) {
  .product-detail-container {
    flex-direction: column;
    gap: 30px;
  }
  
  .product-image-container {
    max-width: 100%;
  }
  
  .product-title {
    font-size: 32px;
  }
}
</style>
