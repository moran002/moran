<template>
  <lay-layer v-model="password" :title="title" :area="['70%', '90%']">
    <lay-json-schema-form :model="model" :schema="passwordSchema"></lay-json-schema-form>
    <div style="width: 100%; text-align: center">
      <lay-button type="primary" @click="save">提交</lay-button>
      <lay-button size="sm" @click="cancel">取消</lay-button>
    </div>
  </lay-layer>
</template>

<script setup lang="ts">
import {passwordSchema, model, password, title, edit,} from "./columns";
import {layer} from "@layui/layui-vue";
import {update} from "@/api/module/system/user";


const showPassword = (row: any) => {
  password.value = true;
  if (!row) {
    layer.msg('请选择样具体用户', {icon: 2, time: 1000})
  } else {
    title.value = '重置密码'
    model.value = JSON.parse(JSON.stringify(row))
  }
}

const cancel = async () => {
  title.value = '';
  password.value = false;
  model.value = {
    id: '',
    nickName: '',
    email: '',
    mobile: '',
    account: '',
    password: '',
    password2: '',
    status: 1,
    remark: '',
    roleIds: [],
  }
}
const save = () => {
  update(model.value).then(({code, msg}) => {
    if (code === 200) {
      layer.msg('保存成功！', {icon: 1, time: 1000})
      password.value = false;
    } else {
      layer.msg(msg, {icon: 2, time: 1000})
    }
  });
}

defineExpose({showPassword});
</script>

<style scoped>

</style>