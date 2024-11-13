<template>
  <lay-container fluid="true" class="role-box">
    <lay-card>
      <lay-form style="margin-top: 10px">
        <lay-row>
          <lay-col :md="5">
            <lay-form-item label="角色名称" label-width="80">
              <lay-input
                  v-model="queryRole.roleName"
                  placeholder="请输入"
                  size="sm"
                  :allow-clear="true"
                  style="width: 98%"
              ></lay-input>
            </lay-form-item>
          </lay-col>
          <lay-col :md="5">
            <lay-form-item label="备注" label-width="80">
              <lay-input
                  v-model="queryRole.remark"
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
                  @click="search"
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
          :page="queryRole"
          :height="'100%'"
          :columns="columns"
          :loading="loading"
          :default-toolbar="true"
          :data-source="list"
          @change="change"
      >
        <template v-slot:toolbar>
          <lay-button v-permission="['/system/role/update']"
                      size="sm"
                      type="primary"
                      @click="updateTable(null)"
          >
            <lay-icon class="layui-icon-addition"></lay-icon>
            新增
          </lay-button
          >
        </template>
        <template v-slot:operator="{ row }">
          <lay-button v-permission="['/system/role/update']"
                      size="xs"
                      border="green"
                      border-style="dashed"
                      @click="updateTable(row)"
          >编辑
          </lay-button>
          <lay-button v-permission="['/system/role/menus']" size="xs" border="blue" border-style="dashed"
                      @click="updatePermissionMenu(row)">分配权限
          </lay-button>
          <lay-button v-permission="['/system/role/del']" size="xs" border="blue" border-style="dashed"
                      @click="delById(row)">删除
          </lay-button>
        </template>
      </lay-table>
    </div>

    <edit-role ref="editRef" @fetchData="fetchData"/>
    <permission-role-menu ref="permissionRef" @fetchData="fetchData"/>
  </lay-container>
</template>
<script setup lang="ts">
import {onMounted, ref} from 'vue'
import {getList, del} from '@/api/module/system/role'
import {layer} from '@layui/layui-vue'
import {list, loading, queryRole, columns} from "@/views/system/role/component/columns";
import EditRole from "@/views/system/role/component/editRole.vue";
import PermissionRoleMenu from "@/views/system/role/component/permissionRoleMenu.vue";

const editRef = ref<any>();
const permissionRef = ref<any>();
const fetchData = async () => {
  loading.value = true;
  let data = await getList(queryRole.value);
  list.value = data.data;
  queryRole.value.total = data.total;
  loading.value = false;
}

const reset = () => {
  queryRole.value = {
    pageNo: 1,
    pageSize: 10,
    total: '',
    roleName: '',
    remark: '',
  }
  fetchData()
}

const search = () => {
  queryRole.value.pageNo = 1;
  fetchData();
}

const change = async (page: any) => {
  queryRole.value.pageNo = page.current
  queryRole.value.pageSize = page.limit
  await fetchData();
}

onMounted(() => {
  fetchData();
})
// =============== 更新 ===============//
const updateTable = async (row: any) => {
  editRef.value.showEdit(row);
}
// =============== 分配权限 ===============//
const updatePermissionMenu = async (row: any) => {
  permissionRef.value.showPermissionMenu(row);
}
// =============== 删除 ===============//
const delById = (row: any) => {
  layer.confirm('确定要删除数据吗?', {
    title: '提示',
    btn: [
      {
        text: '确定',
        callback: (id: any) => {
          del(row).then(({code, msg}) => {
            if (code === 200) {
              layer.msg(msg, {icon: 1, time: 1000})
              window.location.reload();
            } else {
              layer.msg(msg, {icon: 2, time: 1000})
            }
            layer.close(id);
          })
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
.role-box {
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

.isChecked {
  display: inline-block;
  background-color: #e8f1ff;
  color: red;
}
</style>