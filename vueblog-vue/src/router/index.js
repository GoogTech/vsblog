import Vue from 'vue'
import VueRouter from 'vue-router'
import Login from '../views/Login.vue'
import BlogEdit from '../views/BlogEdit.vue'
import BlogDetail from "../views/BlogDetail";
import Blog from "../views/Blog";


Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Index',
        redirect: {name: "Blog"}
    },
    {
        path: '/blog', //原blogs
        name: 'Blog',  //原Blogs
        component: Blog //原Blogs
    },
    {
        path: '/login',
        name: 'Login',
        component: Login
    },
    {
        path: '/blog/add',
        name: 'BlogAdd',
        component: BlogEdit,
        meta:{
            requireAuth: true //需要登录才能访问
        }
    },
    {
        path: '/blog/:blogId',
        name: 'BlogDetail',
        component: BlogDetail
    },
    {
        path: '/blog/:blogId/edit',
        name: 'BlogEdit',
        component: BlogEdit,
        meta: {
            requireAuth: true //需要登录才能访问
        }
    },
    {
        path: '/blog/delete/:blogId',
        meta: {
            requireAuth: true //需要登录才能访问
        }
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
