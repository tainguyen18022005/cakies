<template>
  <div class="home-wrapper">
    <!-- Hero Section -->
    <div class="hero">
      <div class="hero-overlay"></div>
      <div class="hero-content">
        <h1>Nghệ Thuật Bánh Thủ Công</h1>
        <p>Từ những nguyên liệu hảo hạng nhất, tạo nên hương vị Pháp đích thực.</p>
        <button class="btn mt-4" @click="scrollToProducts">Xem Thực Đơn</button>
      </div>
    </div>

    <div class="container my-5" id="menu-section">
      <!-- Category Filter -->
      <div class="category-filter mb-4 text-center">
        <button 
          class="btn-category" 
          :class="{ active: !selectedCategory }" 
          @click="filterByCategory(null)"
        >
          Tất cả
        </button>
        <button 
          v-for="cat in categories" 
          :key="cat.id" 
          class="btn-category" 
          :class="{ active: selectedCategory === cat.id }"
          @click="filterByCategory(cat.id)"
        >
          {{ cat.name }}
        </button>
      </div>

      <!-- Loading State -->
      <div v-if="loading" class="text-center py-5">
        <p>Đang tải thực đơn...</p>
      </div>

      <!-- Product Grid -->
      <div v-else-if="products.length > 0">
        <div class="product-grid">
          <div v-for="product in products" :key="product.id" class="product-card">
            <router-link :to="`/product/${product.id}`" class="product-image-wrapper">
              <img :src="product.imageUrl" :alt="product.name" class="product-img" />
            </router-link>
            <div class="product-info">
              <router-link :to="`/product/${product.id}`" class="product-title-link">
                <h3 class="product-title">{{ product.name }}</h3>
              </router-link>
              <p class="product-price">{{ formatPrice(product.price) }}</p>
            </div>
          </div>
        </div>

        <div class="wavy-divider"></div>

        <!-- Pagination -->
        <div class="pagination mt-4 text-center" v-if="totalPages > 1">
          <button 
            class="btn-outline btn-page" 
            :disabled="currentPage === 0" 
            @click="changePage(currentPage - 1)">
            Trước
          </button>
          <span class="page-info">Trang {{ currentPage + 1 }} / {{ totalPages }}</span>
          <button 
            class="btn-outline btn-page" 
            :disabled="currentPage >= totalPages - 1" 
            @click="changePage(currentPage + 1)">
            Sau
          </button>
        </div>
      </div>
      
      <div v-else class="text-center py-5">
        <p>Không có sản phẩm nào.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import api from '../services/api';

const categories = ref([]);
const products = ref([]);
const selectedCategory = ref(null);
const loading = ref(true);
const currentPage = ref(0);
const totalPages = ref(0);

const fetchCategories = async () => {
  try {
    const res = await api.get('/categories');
    categories.value = res.data;
  } catch (error) {
    console.error("Lỗi khi tải danh mục", error);
  }
};

const fetchProducts = async (categoryId = null, page = 0) => {
  loading.value = true;
  try {
    let url = `/products?page=${page}&size=10`;
    if (categoryId) {
      url += `&categoryId=${categoryId}`;
    }
    const res = await api.get(url);
    products.value = res.data.content;
    totalPages.value = res.data.totalPages;
    currentPage.value = res.data.number;
  } catch (error) {
    console.error("Lỗi khi tải sản phẩm", error);
  } finally {
    loading.value = false;
  }
};

const filterByCategory = (categoryId) => {
  selectedCategory.value = categoryId;
  fetchProducts(categoryId, 0);
};

const changePage = (page) => {
  fetchProducts(selectedCategory.value, page);
};

const formatPrice = (price) => {
  return new Intl.NumberFormat('vi-VN', { style: 'currency', currency: 'VND' }).format(price);
};

const scrollToProducts = () => {
  const el = document.getElementById('menu-section');
  if (el) {
    el.scrollIntoView({ behavior: 'smooth' });
  }
};

onMounted(() => {
  fetchCategories();
  fetchProducts();
});
</script>

<style scoped>
.hero {
  position: relative;
  width: 100%;
  height: 100vh;
  min-height: 600px;
  background-image: url('https://images.unsplash.com/photo-1578985545062-69928b1d9587?q=80&w=2000&auto=format&fit=crop');
  background-size: cover;
  background-position: center;
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
}

.hero-overlay {
  position: absolute;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: linear-gradient(
    to bottom,
    rgba(43,38,34,0.35) 0%,
    rgba(43,38,34,0) 30%,
    rgba(43,38,34,0.8) 100%
  );
  z-index: 1;
}

.hero-content {
  position: relative;
  z-index: 2;
  color: #FAF6F0;
  max-width: 800px;
  padding: 0 20px;
}

.hero-content h1 {
  font-size: 48px;
  margin-bottom: 15px;
  color: #ffffff;
  text-shadow: 0 2px 4px rgba(0,0,0,0.5);
}

.hero-content p {
  font-size: 18px;
  font-family: var(--font-body);
  font-weight: 300;
  margin-bottom: 20px;
  color: #e0dcd7;
}

.category-filter {
  margin-top: 20px;
}

.btn-category {
  background: none;
  border: 1px solid var(--border-color);
  padding: 10px 24px;
  margin: 5px;
  border-radius: 30px;
  cursor: pointer;
  font-family: var(--font-body);
  font-size: 13px;
  text-transform: uppercase;
  letter-spacing: 1px;
  color: var(--text-main);
  transition: all 0.3s ease;
}

.btn-category.active, .btn-category:hover {
  background-color: var(--text-main);
  color: var(--primary-bg);
  border-color: var(--text-main);
}

/* Product Card Redesign */
.product-card {
  background-color: transparent;
  transition: transform 0.3s ease;
}

.product-card:hover {
  transform: translateY(-5px);
}

.product-image-wrapper {
  display: block;
  width: 100%;
  aspect-ratio: 4 / 5;
  overflow: hidden;
  border-radius: 8px;
  margin-bottom: 15px;
  background-color: var(--border-color);
}

.product-img {
  width: 100%;
  height: 100%;
  object-fit: cover;
  transition: transform 0.5s ease;
}

.product-card:hover .product-img {
  transform: scale(1.05);
}

.product-info {
  text-align: center;
}

.product-title-link {
  text-decoration: none;
}

.product-title {
  font-size: 20px;
  margin-bottom: 8px;
  color: var(--text-main);
  transition: color 0.2s;
}

.product-title-link:hover .product-title {
  color: var(--accent-main);
}

.product-price {
  color: var(--accent-sub);
  font-weight: 600;
  font-size: 16px;
  font-family: var(--font-body);
  font-variant-numeric: tabular-nums;
}

.pagination {
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 20px;
}

.btn-page {
  padding: 8px 20px;
}

.page-info {
  font-family: var(--font-body);
  font-size: 14px;
  font-weight: 500;
  color: var(--text-main);
}

@media (max-width: 768px) {
  .hero-content h1 {
    font-size: 32px;
  }
  .product-grid {
    grid-template-columns: repeat(auto-fill, minmax(160px, 1fr));
    gap: 20px;
  }
}
</style>
