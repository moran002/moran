<template>
  <lay-layer v-model="edit" :title="title" :area="['70%', '90%']">
    <lay-json-schema-form :model="updateRole" :schema="schema" ></lay-json-schema-form>
    <div style="width: 100%; text-align: center">
      <lay-button type="primary"  @click="save">提交</lay-button>
      <lay-button size="sm" @click="cancel">取消</lay-button>
    </div>
  </lay-layer>
</template>

<script setup lang="ts">

import {edit, updateRole, schema, title} from "./columns";
import {update} from "@/api/module/system/role";
import {layer} from "@layui/layui-vue";

const emit = defineEmits(['fetchData']);
const showEdit = (row: any) => {
  edit.value = true;
  if (!row) {
    title.value = '添加'
  }else {
    title.value = '编辑'
    updateRole.value = JSON.parse(JSON.stringify(row))
  }
}

const cancel = () => {
  title.value = '';
  edit.value = false;
  updateRole.value = {
    id: '',
    name: '',
    remark: '',
    menuIds:[],
  }
}
const save = () => {
  update(updateRole.value).then(({code, msg}) => {
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

<style scoped>

</style>