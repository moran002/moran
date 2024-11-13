<template>
  <lay-container fluid="true" class="menu-box">
    <lay-card>
      <lay-form style="margin-top: 10px">
        <lay-row>
          <lay-col :md="5">
            <lay-form-item label="菜单名称" label-width="80">
              <lay-input
                  v-model="queryMenu.name"
                  placeholder="请输入"
                  size="sm"
                  :allow-clear="true"
                  style="width: 98%"
              ></lay-input>
            </lay-form-item>
          </lay-col>
          <lay-col :md="5">
            <lay-form-item label-width="20">
              <lay-button
                  style="margin-left: 20px"
                  type="normal"
                  size="sm"
                  @click="fetchData"
              >
                查询
              </lay-button>
              <lay-button size="sm" @click="reset"> 重置</lay-button>
            </lay-form-item>
          </lay-col>
        </lay-row>
      </lay-form>
    </lay-card>
    <!-- table -->
    <div class="table-box">
      <lay-table
          :height="`100%`"
          ref="tableRef6"
          :loading="loading"
          children-column-name="children"
          :columns="columns"
          :data-source="list"
          :default-toolbar="false"
          :expand-index="1"
      >
        <template #toolbar>
          <lay-button v-permission="['/system/menu/update']"
                      size="sm"
                      @click="updateTable(null)"
                      type="normal"
          >
            新建
          </lay-button>
        </template>
        <template #name="{ row }">
          <lay-icon :class="row.icon"></lay-icon> &nbsp;&nbsp;
          {{ row.name }}
        </template>
        <template #option="{ row }">
          <lay-button v-permission="['/system/menu/update']"
                      @click="updateTable( row)"
                      size="xs"
                      border="green"
                      border-style="dashed"
          >
            修改
          </lay-button>
          <lay-button v-permission="['/system/menu/del']"
                      @click="delById(row)"
                      size="xs"
                      border="red"
                      border-style="dashed"
          >
            删除
          </lay-button>
        </template>
        <template #type="{ row }">
          <div v-show="row.type === 1">
            <lay-tag color="#165DFF" variant="light">菜单</lay-tag>
          </div>
          <div v-show="row.type === 2">
            <lay-tag color="#2dc570" variant="light">按钮</lay-tag>
          </div>
        </template>

        <template #isShow="{ row }">
          <div v-show="row.isShow === true">
            <lay-tag color="#165DFF" variant="light">是</lay-tag>
          </div>
          <div v-show="row.isShow === false">
            <lay-tag color="#2dc570" variant="light">否</lay-tag>
          </div>
        </template>
      </lay-table>
    </div>

    <edit-menu ref="editRef" @fetch-data="fetchData"/>
  </lay-container>
</template>
<script setup lang="ts">
import {defineComponent, onMounted, reactive, ref, toRefs} from 'vue'
import {layer} from '@layui/layui-vue'
import {getList, update, del} from '@/api/module/system/menu'
import {loading, title, edit, columns, list, queryMenu, updateMenu} from './columns'
import EditMenu from "@/views/system/menu/editMenu.vue";

const editRef = ref<any>();
const reset = async () => {
  queryMenu.value.name = ''
  await fetchData()
}
const fetchData = async () => {
  loading.value = true;
  await getList(queryMenu.value).then((res) => {
    list.value = res.data;
    loading.value = false;
  });
}

onMounted(() => {
  fetchData()
})

const updateTable = async (row: any) => {
  editRef.value.showEdit(row);
}

// =======================  删除 ========================//
const delById = (row: any) => {
  layer.confirm('确定要删除数据吗?', {
    title: '提示',
    btn: [
      {
        text: '确定',
        callback: (id: any) => {
          del(row)
          layer.msg('您已成功删除')
          layer.close(id)
          window.location.reload();
        }
      },
      {
        text: '取消',
        callback: (id: any) => {
          layer.msg('您已取消操作')
          layer.close(id)
        }
      }
    ]
  })
}

</script>

<style scoped>
.menu-box {
  width: calc(100vw - 220px);
  height: calc(100vh - 110px);
  margin-top: 10px;
  box-sizing: border-box;
  overflow: hidden;
}

.top-search {
  margin-top: 10px;
  padding: 10px;
  height: 40px;
  border-radius: 4px;
  background-color: #fff;
}

.table-box {
  margin-top: 10px;
  padding: 10px;
  height: 700px;
  width: 100%;
  border-radius: 4px;
  box-sizing: border-box;
  background-color: #fff;
}

.search-input {
  display: inline-block;
  width: 98%;
  margin-right: 10px;
}

.table-style {
  margin-top: 10px;
}

.isChecked {
  display: inline-block;
  background-color: #e8f1ff;
  color: red;
}
</style>