<template>
  <div class="login-wrap">
    <div class="login-root">
      <div class="login-main">
<!--        <img class="login-one-ball"-->
<!--             src="https://assets.codehub.cn/micro-frontend/login/fca1d5960ccf0dfc8e32719d8a1d80d2.png"/>-->
        <img class="login-two-ball"
             src="https://assets.codehub.cn/micro-frontend/login/4bcf705dad662b33a4fc24aaa67f6234.png"/>
        <div class="login-container">
          <div class="login-side">
            <div class="login-bg-title">
              <h1>layui vue admin</h1>
              <h3 style="margin: 20px auto">
                开 箱 即 用 的 layui vue 企 业 级 前 端 模 板
              </h3>
            </div>
          </div>
          <div class="login-ID">
            <div style="font-size: 22px; margin-bottom: 15px; margin-top: 5px">
              🎯 Sign in
            </div>
            <lay-tab type="brief" v-model="method">
              <lay-tab-item title="用户名" id="1">
                <div style="height: 250px">
                  <lay-form-item :label-width="0">
                    <lay-input :allow-clear="true" prefix-icon="layui-icon-username" placeholder="用户名"
                               v-model="state.loginForm.account"></lay-input>
                  </lay-form-item>
                  <lay-form-item :label-width="0">
                    <lay-input :allow-clear="true" prefix-icon="layui-icon-password" placeholder="密码" password
                               type="password" v-model="state.loginForm.password"></lay-input>
                  </lay-form-item>
                  <lay-form-item :label-width="0">
                    <div style="width: 264px; display: inline-block">
                      <lay-input :allow-clear="true" prefix-icon="layui-icon-vercode" placeholder="验证码"
                                 v-model="state.loginForm.verifyCode"></lay-input>
                    </div>

                    <div class="login-captach" @click="toRefreshImg">
                      <img style="width: 100%" :src="state.imgUrl" alt="获取验证码"/>
                    </div>
                  </lay-form-item>
                  <lay-checkbox value="" name="like" v-model="remember" skin="primary" label="1">记住密码</lay-checkbox>
                  <lay-form-item :label-width="0">
                    <lay-button style="margin-top: 20px" type="primary" :loading="loging" :fluid="true"
                                loadingIcon="layui-icon-loading" @click="loginSubmit">登录
                    </lay-button>
                  </lay-form-item>
                </div>
              </lay-tab-item>
            </lay-tab>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>

<script lang="ts">
import {login, captcha} from '@/api/module/user'
import {defineComponent, onMounted, reactive, ref} from 'vue'
import {useRouter} from 'vue-router'
import {useUserStore} from '@/store/user'
import {layer} from '@layui/layer-vue'

export default defineComponent({

  setup() {
    const router = useRouter()
    const userStore = useUserStore()
    const method = ref('1')
    const loging = ref(false);
    const remember = ref(false)
    const state = reactive({
      imgUrl: '',
      loginForm: {
        account: 'admin',
        password: '123456',
        verifyCode: '',
        captchaId: '',
      }
    })

    const loginSubmit = async () => {
      loging.value = true;
      login(state.loginForm).then(({code, msg, data}) => {
        if (code === 200) {
          setTimeout(() => {
            loging.value = false;
            layer.msg(msg, {icon: 1}, async () => {
              userStore.token = data
              await userStore.loadMenus()
              await userStore.loadPermissions()
              router.push('/')
            })
          }, 1000)
        }else {
          loging.value = false;
        }
      })
    }
    onMounted(() => {
      toRefreshImg()
    })

    const toRefreshImg = async () => {
      let data = await captcha()
      state.imgUrl = data.data.imgUrl
      state.loginForm.captchaId = data.data.captchaId
    }

    return {
      toRefreshImg,
      loginSubmit,
      state,
      remember,
      method,
      loging
    }
  }
})
</script>

<style scoped>
.login-captach {
  display: inline-block;
  vertical-align: bottom;
  width: 108px;
  height: 40px;
  color: var(--global-primary-color);
  margin-left: 8px;
  border-radius: 4px;
  border: 1px solid hsla(0, 0%, 60%, 0.46);
  transition: border 0.2s;
  box-sizing: border-box;
  background: #fff;
  overflow: hidden;
  cursor: pointer;
}

.login-one-ball {
  opacity: 0.4;
  position: absolute;
  max-width: 568px;
  left: -400px;
  bottom: 0px;
}

.login-two-ball {
  opacity: 0.4;
  position: absolute;
  max-width: 320px;
  right: -200px;
  top: -60px;
}

.login-wrap {
  position: fixed;
  top: 0;
  left: 0;
  bottom: 0;
  right: 0;
  overflow: auto;
  min-width: 600px;
  z-index: 9;
  background-image: url(https://assets.codehub.cn/micro-frontend/login/f7eeecbeccefe963298c23b54741d473.png);
  background-repeat: no-repeat;
  background-size: cover;
  min-height: 100vh;
}

.login-wrap :deep(.layui-input-block) {
  margin-left: 0 !important;
}

.login-root {
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  display: flex;
  justify-content: center;
  width: 100%;
  min-width: 320px;
  background-color: initial;
}

.login-main {
  position: relative;
  display: block;
}

.logo-container {
  max-width: calc(100vw - 28px);
  margin-bottom: 40px;
  text-align: center;
  display: flex;
  align-items: center;
  justify-content: center;
}

.logo-container .logo {
  display: inline-block;
  height: 30px;
  width: 143px;
  background: url() no-repeat 50%;
  background-size: contain;
  cursor: pointer;
}

.login-container {
  position: relative;
  overflow: hidden;
  width: 940px;
  height: 540px;
  max-width: calc(100vw - 28px);
  border-radius: 4px;
  background: hsla(0, 0%, 100%, 0.5);
  backdrop-filter: blur(30px);
  display: flex;
  box-shadow: 6px 6px 12px 4px rgba(0, 0, 0, 0.1);
}

.login-side {
  padding: 40px 20px 20px;
  background-color: var(--global-primary-color);
  flex: 1;
  height: 100%;
}

.login-bg-title {
  flex: 1;
  height: 84%;
  color: #fff;
  text-align: center;
  background-image: url('../../assets/login/login-bg.svg');
  background-repeat: no-repeat;
  background-position: bottom;
  background-size: contain;
  text-align: center;
  min-width: 200px;
}

.login-ID {
  padding: 20px 30px;
  width: 380px;
  min-width: 380px;
}

.login-container .layui-tab-head {
  background: transparent;
}

.login-container .layui-input-wrapper {
  margin-top: 10px;
  margin-bottom: 10px;
}

.login-container .layui-input-wrapper {
  margin-top: 12px;
  margin-bottom: 12px;
}

.login-container .assist {
  margin-top: 5px;
  margin-bottom: 5px;
  letter-spacing: 2px;
}

.login-container .layui-btn {
  margin: 10px 0px 10px 0px;
  letter-spacing: 2px;
  height: 40px;
}

.login-container .layui-line-horizontal {
  letter-spacing: 2px;
  margin-bottom: 34px;
  margin-top: 24px;
}

.other-ways {
  display: flex;
  justify-content: space-between;
  margin: 0;
  padding: 0;
  list-style: none;
  font-size: 14px;
  font-weight: 400;
}

.other-ways li {
  width: 100%;
}

.line-container {
  justify-content: center;
  align-items: center;
  text-align: center;
  cursor: pointer;
}

.line-container .icon {
  height: 28px;
  width: 28px;
  margin-right: 0;
  vertical-align: middle;
  border-radius: 50%;
  background: #fff;
  box-shadow: 0 1px 2px 0 rgb(9 30 66 / 4%), 0 1px 4px 0 rgb(9 30 66 / 10%),
  0 0 1px 0 rgb(9 30 66 / 10%);
}

.line-container .text {
  display: block;
  margin: 12px 0 0;
  font-size: 12px;
  color: #8592a6;
}

:deep(.layui-tab-title .layui-this) {
  background-color: transparent;
}
</style>
