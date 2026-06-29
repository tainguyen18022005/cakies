<template>

<div class="login">

    <div class="card">

        <h1>Cakies Shop</h1>

        <p>Đăng nhập để tiếp tục</p>

        <form @submit.prevent="submit">

            <Input

                label="Tên đăng nhập"

                placeholder="Nhập Username"

                v-model="username"

            />

            <Input

                label="Mật khẩu"

                placeholder="Nhập Password"

                type="password"

                v-model="password"

            />

            <p
                class="error"
                v-if="error"
            >
                {{ error }}
            </p>

            <Button

                type="submit"

                :loading="loading"

            >

                Đăng nhập

            </Button>

        </form>

    </div>

</div>

</template>

<script setup>

import { ref } from "vue";

import { useRouter } from "vue-router";

import { useAuthStore } from "../stores/auth";

import Button from "../components/Button.vue";

import Input from "../components/Input.vue";

const router = useRouter();

const auth = useAuthStore();

const username = ref("");

const password = ref("");

const loading = ref(false);

const error = ref("");

async function submit(){

    error.value="";

    if(username.value.trim()===""){

        error.value="Vui lòng nhập Username";

        return;

    }

    if(password.value.trim()===""){

        error.value="Vui lòng nhập Password";

        return;

    }

    loading.value=true;

    try{

        await auth.login({

            username:username.value,

            password:password.value

        });

        router.push("/");

    }catch(e){

        error.value="Tên đăng nhập hoặc mật khẩu không đúng.";

    }

    loading.value=false;

}

</script>

<style scoped>

.login{

    min-height:100vh;

    display:flex;

    justify-content:center;

    align-items:center;

    background:#fff7f2;

}

.card{

    width:420px;

    background:white;

    padding:40px;

    border-radius:15px;

    box-shadow:0 15px 40px rgba(0,0,0,.15);

}

h1{

    text-align:center;

    margin-bottom:5px;

    color:#ff6b35;

}

p{

    text-align:center;

    margin-bottom:25px;

}

.error{

    color:red;

    margin-bottom:18px;

    text-align:center;

    font-size:14px;

}

</style>