<template>
  <nav class="navbar" :class="{ 'navbar--scrolled': scrolled }">
    <div class="container nav-container">
      <router-link to="/" class="logo">Cakies</router-link>
      <div class="nav-links">
        <router-link to="/">Trang chủ</router-link>
        <router-link to="/cart" class="cart-icon">
          Giỏ hàng
          <span v-if="cartStore.itemCount > 0" class="cart-count">{{ cartStore.itemCount }}</span>
        </router-link>
      </div>
    </div>
  </nav>

  <main>
    <router-view />
  </main>
</template>

<script setup>
import { ref, onMounted, onUnmounted } from 'vue';
import { useCartStore } from './stores/cart';

const cartStore = useCartStore();
const scrolled = ref(false);

const handleScroll = () => {
  scrolled.value = window.scrollY > 60;
};

onMounted(() => {
  cartStore.fetchCart();
  window.addEventListener('scroll', handleScroll);
});

onUnmounted(() => {
  window.removeEventListener('scroll', handleScroll);
});
</script>

<style>
/* App specific styles if needed */
</style>
