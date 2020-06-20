// 引入axios依赖
import axios from 'axios'
// 引入element-ui依赖
import Element from 'element-ui'
// 引入路由
import router from './router'
// 引入存储
import store from './store'

// 自定义全局请求头(后台)
axios.defaults.baseURL = "http://49.233.79.152:8080"

// 自定义前置拦截
axios.interceptors.request.use(config => {
    return config
})

// 自定义后置拦截
axios.interceptors.response.use(response => {
        let result = response.data

        console.log("-------------------------")
        console.log(result)
        console.log("-------------------------")

        if(result){
            result.message = response.data.msg
        }
        if (result.code == 200) {
            //Element.Message.success("登录成功, 欢迎回来 !")
            Element.Message.success(result.message)
            return response //放行
        } else {
            //Element.Message.error('密码错误, 请尝试重新登录 !');
            Element.Message.error(result.message)
            return Promise.reject(response.data.msg)//抛出异常, 阻止进入"$axios.post(.."
        }
    },

    // 捕获并处理后台异常信息
    error => {
        // 使得异常信息更加友好
        if (error.response.data) { //data不为空时
            error.message = error.response.data.msg
            console.log("-------------------------")
            console.log(error.message)
            console.log("-------------------------")
        }

        if (error.response.status === 401) { //Shiro认证错误
            store.commit("REMOVE_INFO") //清除用户信息:token及userInfo
            router.push("/login") //跳转到登录页面
        }
        Element.Message.error(error.message);
        return Promise.reject(error)//抛出异常, 阻止进入"$axios.post(.."
    }
)
