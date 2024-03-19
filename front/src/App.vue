<script setup lang="ts">
import { RouterLink, RouterView } from 'vue-router'
</script>

<template>
  <header>
      <nav>
        <RouterLink to="/">Home</RouterLink>
        <RouterLink to="/write">About</RouterLink>
      </nav>
  </header>

  <RouterView />
</template>

<style scoped>
</style>
