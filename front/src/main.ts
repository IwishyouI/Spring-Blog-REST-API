// import './assets/main.css'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import router from './router'
// @ts-ignore
import App from "@/App.vue";
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
import "bootstrap/dist/css/bootstrap.min.css"

const app = createApp(App)

app.use(createPinia())
app.use(router)

app.use(ElementPlus)

app.mount('#app')
