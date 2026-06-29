<template>

<div class="group">

    <label>{{ label }}</label>

    <input

        :type="type"

        :placeholder="placeholder"

        :value="modelValue"

        @input="$emit('update:modelValue',$event.target.value)"

    />

</div>

</template>

<script setup>

defineProps({

    modelValue:String,

    label:String,

    placeholder:String,

    type:{
        type:String,
        default:"text"
    }

})

defineEmits([
    "update:modelValue"
])

</script>

<style scoped>

.group{

    margin-bottom:18px;

}

label{

    display:block;

    margin-bottom:8px;

    font-weight:600;

}

input{

    width:100%;

    padding:13px;

    border:1px solid #ccc;

    border-radius:8px;

    outline:none;

    transition:.2s;

    font-size:15px;

}

input:focus{

    border-color:#ff6b35;

    box-shadow:0 0 5px rgba(255,107,53,.3);

}

</style>