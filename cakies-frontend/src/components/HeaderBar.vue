<template>
  <header>
    <div class="logo">🍰 Cakie
    <div class="right">
      <span>
        Xin chào,

        <b>{{ auth.username }}</b>
      </span>

      <button @click="logout">Đăng xuất</button>
    </div>
  </header>
</template>

<script setup>
import { useRouter } from "vue-router";

import { useAuthStore } from "../stores/auth";

const router = useRouter();

const auth = useAuthStore();

function logout() {
  auth.logout();

  router.push("/login");
}
</script>

<style scoped>
header {
  height: 70px;

  background: #ff6b35;

  color: white;

  display: flex;

  justify-content: space-between;

  align-items: center;

  padding: 0 30px;
}

.logo {
  font-size: 24px;

  font-weight: bold;
}

.right {
  display: flex;

  gap: 20px;

  align-items: center;
}

button {
  border: none;

  padding: 10px 18px;

  border-radius: 8px;

  cursor: pointer;
}

button:hover {
  background: #eee;
}
</style>
