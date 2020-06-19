import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  //定义全局参数
  state: {
    token: localStorage.getItem("token"), //jwt
    userInfo:JSON.parse(sessionStorage.getItem("userInfo"))
  },

  //set
  mutations: {
    //初始化并存储token以及userInfo
    SET_TOKEN:(state,token)=>{
      state.token = token
      localStorage.setItem("token",token)
    },
    SET_USERINFO:(state,userInfo)=>{
      state.userInfo = userInfo
      sessionStorage.setItem("userInfo",JSON.stringify(userInfo))
    },
    //删除token及userInfo
    REMOVE_INFO:(state)=>{
      state.token = '';
      state.userInfo = {};
      localStorage.setItem("token",'')
      sessionStorage.setItem("userInfo",JSON.stringify(''))
    }

  },
  //get: 获取用户信息及token
  getters: {
    getUserInfo:state => {
      return state.userInfo
    },
    getToken:state => {
      return state.token
    }

  },
  actions: {
  },
  modules: {
  }
})
