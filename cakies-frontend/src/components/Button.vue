<template>
    <button
        :type="type"
        :disabled="disabled || loading"
        class="cakies-btn"
        @click="$emit('click')"
    >
        <span v-if="loading">Đang xử lý...</span>
        <span v-else>
            <slot />
        </span>
    </button>
</template>

<script setup>
defineProps({
    type: {
        type: String,
        default: "button"
    },
    loading: {
        type: Boolean,
        default: false
    },
    disabled: {
        type: Boolean,
        default: false
    }
});

defineEmits(["click"]);
</script>

<style scoped>

.cakies-btn{

    width:100%;
    padding:14px;

    border:none;
    border-radius:10px;

    cursor:pointer;

    background:#ff6b35;
    color:white;

    font-size:16px;
    font-weight:bold;

    transition:.25s;

}

.cakies-btn:hover{

    background:#ff5722;

}

.cakies-btn:disabled{

    opacity:.6;
    cursor:not-allowed;

}

</style>