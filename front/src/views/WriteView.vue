<script setup lang="ts">

// noinspection TypeScriptCheckImport
import {ref} from "vue";
import axios from 'axios';

const title = ref("");
const content = ref("");

const write = function () {

    axios.post("http://localhost:8080/posts",
        {
            title: title.value,
            content: content.value
        })
}
</script>

<template>
    <div class="mt-3">
        <el-input v-model="title" placeholder="제목을 입력해주세요"></el-input>
    </div>
    <div class="mt-3">
        <el-input v-model="content" type="textarea" row="15"></el-input>
    </div>
    <div class="mt-3">

        <el-button type="primary" @click="write()">글 작성완료</el-button>
    </div>
</template>
