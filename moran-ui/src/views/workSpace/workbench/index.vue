<template>
  <lay-container fluid="true" style="padding: 10px">
  </lay-container>
</template>
<script lang="ts">

</script>

<style lang="less" scoped>
.project-grids {
  :deep(.layui-card-body) {
    padding: 0;
  }
}

.project-grid:hover {
  box-shadow: 1px 1px 17px #ccc;
}
.project-grid {
  padding: 24px;
  background-color: #f8f8f8;
  border-radius: 4px;
  margin: 10px;
  color: #777;

  .project-grid-title {
    padding-bottom: 10px;

    i {
      margin-right: 10px;
      font-size: 24px;
      color: #009688;
    }

    a {
      line-height: 24px;
      font-size: 16px;
      vertical-align: top;
    }
    .layui-icon {
      font-weight: 600;
    }
  }

  .project-grid-center {
    height: 44px;
    line-height: 22px;
    margin-bottom: 10px;
    overflow: hidden;
  }

  .project-grid-footer {
    position: relative;

    a {
      color: #777;
      font-size: 12px;
      text-overflow: ellipsis;
      word-break: break-all;
    }

    span {
      color: #ccc;
      font-size: 12px;
      position: absolute;
      right: 0;
    }
  }
}

.dynamic {
  padding: 15px 0;
  border-bottom: 1px solid #eee;
  display: -webkit-flex;
  display: flex;

  .layui-status-img img {
    width: 32px;
    height: 32px;
    border-radius: 50%;
    background-color: #009688;
    margin-right: 15px;
  }

  a {
    color: #0481ff;
  }

  span {
    color: #bbb;
  }
}

.links {
  padding: 10px;
}

.links a {
  width: 25%;
  font-size: 14px;
  margin-top: 8px;
  margin-bottom: 8px;
  display: inline-block;
  color: #666;
}
.links a:hover {
  color: #11be73;
}

.news {
  display: block;
  line-height: 60px;
  text-align: center;
  background-color: #009688 !important;
  color: #fff !important;
  margin-bottom: 10px;
}

#main {
  height: 300px;
  width: 100%;
}
</style>
