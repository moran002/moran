<template>
  <lay-layer v-model="edit" :title="title" :area="['70%', '90%']">
    <lay-json-schema-form :model="model" :schema="schema" ></lay-json-schema-form>
    <div style="width: 100%; text-align: center">
      <lay-button type="primary"  @click="save">提交</lay-button>
      <lay-button size="sm" @click="cancel">取消</lay-button>
    </div>
  </lay-layer>
</template>

<script setup lang="ts">
import {edit, model, options, password, title, schema} from "./columns";
import {update} from "@/api/module/system/user";
import {layer} from "@layui/layui-vue";
import {roles} from "@/api/module/dictionary";

const showEdit = (row: any) => {
  edit.value = true;
  findRoles();
  if (!row) {
    title.value = '添加'
  }else {
    title.value = '编辑'
    schema.password.hidden = true;
    model.value = JSON.parse(JSON.stringify(row))
  }
}

const findRoles = async () => {
  let data = await roles();
  options.value = data.data;
}

const cancel = async () => {
  title.value = '';
  edit.value = false;
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
const emit = defineEmits(['fetchData']);
const save = () => {
  update(model.value).then(({code, msg}) => {
    if (code === 200) {
      layer.msg('保存成功！', {icon: 1, time: 1000})
      edit.value = false;
      emit('fetchData')
    } else {
      layer.msg(msg, {icon: 2, time: 1000})
    }
  });
}

defineExpose({showEdit})
</script>

<style >
.layui-col-space10 {
  margin: 0px !important;
}
</style>