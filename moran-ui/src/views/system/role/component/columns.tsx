import {reactive, ref} from "vue";

export const columns =  [
    {title: '编号', width: '80px', key: 'id', fixed: 'left'},
    {title: '角色名称', width: '80px', key: 'name'},
    {title: '备注', width: '260px', key: 'remark'},
    {title: '创建时间', width: '120px', key: 'createTime'},
    {
        title: '操作',
        width: '150px',
        customSlot: 'operator',
        key: 'operator',
        fixed: 'right'
    }
]

export const loading = ref(true);
export const edit = ref(false);
export const permissionMenu = ref(false);
export const list = ref([])
export const title = ref("")

export const menus = ref([])

export const updateRole= ref({
    id: '',
    name: '',
    remark: '',
    menuIds:[],
})

export const queryRole = ref({
    pageNo: 1,
    pageSize: 10,
    total: '',
    roleName: '',
    remark: '',
})

export const schema = reactive({
    name: {
        label: '角色名称',
        type: 'input',
        props: {
            type: 'text',
            placeholder: '请输入角色名称',
        }
    },
    remark: {
        label: '描述',
        type: 'input',
        props: {
            type: 'text',
            placeholder: '请输入描述',
        }
    },
})