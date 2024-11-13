<template>
  <lay-layer v-model="permissionMenu" :title="title" :area="['70%', '90%']">
    <div style="height: 80%; overflow: auto">
      <lay-tree
          style="margin-left: 40px"
          :tail-node-icon="false"
          :data="menus"
          :showCheckbox="true"
          v-model:checkedKeys="menuIds"
          v-model:expandKeys="updateRole.menuIds"
      >
        <template #title="{ data }">
          <lay-icon :class="data.icon"></lay-icon>
          {{ data.title }}
        </template>
      </lay-tree>
    </div>
    <lay-line></lay-line>
    <div style="width: 100%; text-align: center">
      <lay-button type="primary"  @click="save">提交</lay-button>
      <lay-button size="sm" @click="cancel">取消</lay-button>
    </div>
  </lay-layer>
</template>

<script setup lang="ts">
import {ref} from "vue";
import {title, permissionMenu, updateRole, menus} from "@/views/system/role/component/columns";
import {layer} from "@layui/layui-vue";
import {getMenus} from "@/api/module/dictionary";
import {updateMenus} from "@/api/module/system/role";

const menuIds = ref([]);
const showPermissionMenu = (row: any) => {
  permissionMenu.value = true;
  if (!row) {
    layer.msg('请选择样具体角色', {icon: 2, time: 1000})
  } else {
    title.value = '分配权限'
    updateRole.value = JSON.parse(JSON.stringify(row))
    menuIds.value = row.menuIds
    findMenus();
  }
}

const findMenus = async () => {
  let data = await getMenus();
  menus.value = data.data;
}
const cancel = () => {
  title.value = '';
  permissionMenu.value = false;
  updateRole.value = {
    id: '',
    name: '',
    remark: '',
    menuIds:[],
  }
}

const emit = defineEmits(['fetchData']);
const save = () => {
  updateRole.value.menuIds = menuIds.value;
  updateMenus(updateRole.value).then(({code, msg}) => {
    if (code === 200) {
      layer.msg('保存成功！', {icon: 1, time: 1000})
      permissionMenu.value = false;
      cancel();
      emit("fetchData");
    } else {
      layer.msg(msg, {icon: 2, time: 1000})
    }
  })
}

defineExpose({showPermissionMenu});
</script>

<style scoped>

</style>