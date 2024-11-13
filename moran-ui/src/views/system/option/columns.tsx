import {ref} from "vue";

export const columns = ref([
    {title: '账号', key: 'account'},
    {title: '用户名', key: 'nickName'},
    {title: 'IP地址', key: 'ip'},
    {title: '方法名称', width: '180px', key: 'methodName'},
    {title: ' 类名称', width: '180px', key: 'className',},
    {title: '请求链接', key: 'url'},
    {title: '设备名称',  key: 'deviceName',},
    {title: '耗时', key: 'consumeTime'},
    {title: '操作时间', width: '180px', key: 'optime'},
])

export const page = ref({
    pageNo: 1,
    pageSize: 10,
    total: '',
    layout: ['count', 'prev', 'page', 'next', 'limits', 'refresh', 'skip'],
})

export const queryOption = ref({
    pageNo: 1,
    pageSize: 10,
    account: '',
    nickName: '',
    startTime: '',
    endTime: '',
})
export const loading = ref(true);
export const list = ref([])