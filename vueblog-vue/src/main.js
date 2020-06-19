import Vue from 'vue'
import App from './App.vue'
// 路由
import router from './router'
// 存储
import store from './store'
// 引入element-ui依赖
import Element from 'element-ui'
import "element-ui/lib/theme-chalk/index.css"
// 引入axios依赖
import axios from 'axios'
// 引入自定义axios.js
import "./axios.js"
// 引入mavon-editor
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// 引入permission(路由权限拦截)
import "./permission"
// 引入'复制内容至剪切板'插件
import VueClipboards from 'vue-clipboard2'

// element-ui全局使用
Vue.use(Element)
// axios全局使用
Vue.prototype.$axios = axios
// mavon-editor全局使用
Vue.use(mavonEditor)
// VueClipboards全局使用
Vue.use(VueClipboards);

Vue.config.productionTip = false

new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
