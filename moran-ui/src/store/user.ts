import { defineStore } from 'pinia'
import {routers, permission} from "@/api/module/dictionary";

export const useUserStore = defineStore({
  id: 'user',
  state: () => {
    return {
      token: '',
      userInfo: {},
      permissions: [],
      menus: [],
    }
  },
  actions: {
    async loadMenus(){
      let data = await routers();
      this.menus = data.data
    },
    async loadPermissions(){
      let data = await permission();
      this.permissions = data.data
    }
  },
  persist: {
    storage: localStorage,
    paths: ['token', 'userInfo', 'permissions', 'menus' ],
  }
})