<template>
  <lay-container fluid="true" class="user-box">
    <lay-card>
      <lay-form >
        <lay-row>
          <lay-col :md="5">
            <lay-form-item label="用户账号" label-width="80">
              <lay-input
                  v-model="queryForm.account"
                  placeholder="请输入"
                  :allow-clear="true"
                  style="width: 98%"
              ></lay-input>
            </lay-form-item>
          </lay-col>
          <lay-col :md="5">
            <lay-form-item label="用户名" label-width="80">
              <lay-input
                  v-model="queryForm.nickName"
                  placeholder="请输入"
                  :allow-clear="true"
                  style="width: 98%"
              ></lay-input>
            </lay-form-item>
          </lay-col>
          <lay-col :md="5">
            <lay-form-item label-width="20">
              <lay-button
                  style="margin-left: 20px"
                  type="primary"
                  @click="search"
              >
                查询
              </lay-button>
              <lay-button  @click="reset"> 重置</lay-button>
            </lay-form-item>
          </lay-col>
        </lay-row>
      </lay-form>
    </lay-card>
    <!-- table -->
    <div >
      <lay-table class="table-box"
          :page="page"
          :height="'100%'"
          :columns="columns"
          :loading="loading"
          :default-toolbar="['filter']"
          :data-source="list"
          @change="change"
          :id="list.userId"
      >
        <template v-slot:toolbar>
          <lay-button size="sm" type="primary" @click="updateTable()" v-permission="['/system/user/update']">
            <lay-icon class="layui-icon-addition"></lay-icon>
            新增
          </lay-button>
        </template>
        <template #status="{ row }">
          <lay-switch
              :model-value="row.status"
              @change="changeStatus($event, row)"
          ></lay-switch>
        </template>
        <template #avatar="{ row }">
          <lay-avatar :src="row.avatar"></lay-avatar>
        </template>
        <template v-slot:operator="{ row }">
          <lay-button v-permission="['/system/user/update']"
                      size="xs"
                      type="primary"
                      @click="updateTable( row)"
          >编辑
          </lay-button>
          <lay-button v-permission="['/system/user/update']"
                      @click="updatePassword('修改密码',row)"
                      size="xs"
                      border="primary"
                      border-style="dashed"
          >
            修改密码
          </lay-button>
          <lay-button v-permission="['/system/user/del']"
                      @click="delById(row)"
                      size="xs"
                      border="red"
                      border-style="dashed"
          >
            删除
          </lay-button>

        </template>
      </lay-table>
    </div>

    <update-user ref="editRef" @fetchData="fetchData"/>

    <password ref="passwordRef"/>
  </lay-container>
</template>
<script setup lang="ts">
import {onMounted, ref} from 'vue'
import {layer} from '@layui/layui-vue'
import {getList, del, updateStatus} from "@/api/module/system/user";
import {loading, list, page, queryForm, columns} from "./component/columns";
import UpdateUser from "@/views/system/user/component/updateUser.vue";
import Password from "@/views/system/user/component/password.vue";

const editRef = ref<any>();
const passwordRef = ref<any>();

const reset = () => {
  queryForm.value = {
    pageNo: 1,
    pageSize: 10,
    account: '',
    nickName: '',
  }
  fetchData();
}

const search = () => {
  queryForm.value.pageNo = 1;
  fetchData();
}

const fetchData = async () => {
  loading.value = true;
  await getList(queryForm.value).then((res) => {
    list.value = res.data;
    page.value.total = res.total;
    loading.value = false;
  });
}

const change = async (page: any) => {
  queryForm.value.pageNo = page.current
  queryForm.value.pageSize = page.limit
  await fetchData();
}

// =============== 更新 ===============//
const updateTable = (row: any) => {
  editRef.value.showEdit(row)
}

// =============== 状态变更 ===============//
const changeStatus = (isChecked: boolean, row: any) => {
  row.status = isChecked
  updateStatus(row)
}
// =============== 变更密码 ===============//
const updatePassword = (row: any) => {
  passwordRef.value.showPassword(row)
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

onMounted(() => {
  fetchData();
})
</script>

<style scoped>
.user-box {
  height: calc(100vh - 110px);
  margin-top: 10px;
  box-sizing: border-box;
  overflow: auto;
}


.table-box {
  padding: 10px;
  height: 700px;
  width: 100%;
  border-radius: 4px;
  box-sizing: border-box;
  background-color: #fff;
}
</style>