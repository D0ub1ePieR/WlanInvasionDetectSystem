import { login, logout, getInfo } from '@/api/login'
import { getToken, setToken, removeToken } from '@/utils/auth'
import axios from 'axios'
import global from '../../Global'
import router from '../../router'
import { Message } from 'element-ui'

const wifi = {
  state: {
    token: getToken(),
  },

  mutations: {
    
  },

  actions: {
    
  }
}

export default wifi
