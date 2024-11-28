import {reactive, ref} from "vue";

export const columns = [
    {
        title: '主键',
        key: 'id',
        width: 70
    },
    {
        title: '菜单名称',
        key: 'name',
        customSlot: 'name',
        width: 300
    },
    {
        title: '路由地址',
        key: 'path'
    },
    {
        title: '组件路径',
        key: 'component'
    },
    {
        title: '排序',
        width: '70px',
        key: 'sort',
    },
    {
        title: '可见',
        key: 'isShow',
        customSlot: 'isShow'
    },
    {
        title: '类型',
        key: 'type',
        customSlot: 'type',
    },
    {
        title: 'api',
        key: 'api',
    },
    {
        title: '创建时间',
        key: 'createTime',
    },
    {
        title: '操作',
        key: 'option',
        customSlot: 'option'
    }
]
export const loading = ref(true);
export const edit = ref(false);
export const title = ref('');
export const list = ref([]);

export const updateMenu = ref({
    id: '',
    parentId: '',
    name: '',
    type: 1,
    sort: 0,
    icon: '',
    path: '',
    component: '',
    isShow: true,
    api: '',
})

export const queryMenu = ref({
    name: '',
})

export const schema = reactive({
    parentId: {
        label: 'parentId',
        type: 'input',
        props: {
            type: 'text',
        }
    },
    name: {
        label: '菜单名称',
        type: 'input',
        props: {
            type: 'text',
        }
    },
    path: {
        label: '路由路径',
        type: 'input',
        props: {
            type: 'text',
        }
    },
    component: {
        label: '组件路径',
        type: 'input',
        props: {
            type: 'text',
        }
    },
    api: {
        label: 'api',
        type: 'input',
        props: {
            type: 'text',
        }
    },
    icon: {
        label: '图标',
        type: 'input',
        props: {
            type: 'text',
        }
    },
    sort: {
        label: '排序',
        type: 'input',
        props: {
            type: 'text',
        }
    },
    isShow: {
        label: '是否显示',
        type: 'switch',
        props: {
        }
    },
    type: {
        label: '类型',
        type: 'radio',
        props: {
            options: [
                {
                    label: "菜单",
                    value: 1,
                },
                {
                    label: "按钮",
                    value: 2,
                },
            ]
        }
    },


})