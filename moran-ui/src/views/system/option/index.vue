<template>
  <lay-container fluid="true" class="option-box">
    <lay-card>
      <lay-form style="margin-top: 10px">
        <lay-row>
          <lay-col :md="4">
            <lay-form-item label="用户账号" label-width="80">
              <lay-input v-model="queryOption.account"
                         placeholder="请输入"
                         size="sm"
                         :allow-clear="true"
                         style="width: 98%">
              </lay-input>
            </lay-form-item>
          </lay-col>
          <lay-col :md="4">
            <lay-form-item label="用户名" label-width="80">
              <lay-input
                  v-model="queryOption.nickName"
                  placeholder="请输入"
                  size="sm"
                  :allow-clear="true"
                  style="width: 98%">
              </lay-input>
            </lay-form-item>
          </lay-col>
          <lay-col :md="4">
            <lay-form-item label="开始时间" label-width="80">
              <lay-date-picker
                  size="sm"
                  v-model="queryOption.startTime"
                  type="datetime"
                  placeholder="开始时间"
                  allowClear>
              </lay-date-picker>
            </lay-form-item>
          </lay-col>
          <lay-col :md="4">
            <lay-form-item label="结束时间" label-width="80">
              <lay-date-picker
                  size="sm"
                  v-model="queryOption.endTime"
                  type="datetime"
                  placeholder="结束时间"
                  allowClear>
              </lay-date-picker>
            </lay-form-item>
          </lay-col>
          <lay-col :md="5">
            <lay-form-item label-width="20">
              <lay-button
                  style="margin-left: 20px"
                  type="normal"
                  size="sm"
                  @click="fetchData">
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
          :page="page"
          :height="'100%'"
          :columns="columns"
          :loading="loading"
          :default-toolbar="true"
          :data-source="list"
          @change="change"
      >
      </lay-table>
    </div>
  </lay-container>
</template>
<script setup lang="ts">
import {onMounted} from 'vue'
import {loading, list, columns, page, queryOption} from "@/views/system/option/columns";
import {getList} from "@/api/module/system/option";

const fetchData = async () => {
  loading.value = true;
  getList(queryOption.value).then((res) => {
    list.value = res.data;
    page.value.total = res.total;
    loading.value = false;
  })
}
const reset = () => {
  queryOption.value = {
    pageNo: 1,
    pageSize: 10,
    account: '',
    nickName: '',
    startTime: '',
    endTime: '',
  }
  fetchData()
}
const change = async (page: any) => {
  queryOption.value.pageNo = page.current
  queryOption.value.pageSize = page.limit
  await fetchData();
}
onMounted(() => {
  fetchData()
})
</script>

<style scoped>
.option-box {
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

.oneRow {
  width: 180px;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: left;
}

.title {
  height: 40px;
  line-height: 40px;
  padding: 0 10px;
  display: inline-block;
  background: #f7f7f7;
  border-top: 1px solid #e8e8e8;
  border-left: 1px solid #e8e8e8;
}

.content {
  height: 40px;
  line-height: 40px;
  padding: 0 3px 0 10px;
  border-top: 1px solid #e8e8e8;
  border-left: 1px solid #e8e8e8;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
  text-align: left;
}

.borderR {
  border-right: 1px solid #e8e8e8;
}

.borderB {
  border-bottom: 1px solid #e8e8e8;
}
</style>