import {reactive, ref} from "vue";

export const columns =  ref([
    {title: '选项', width: '60px', type: 'checkbox', fixed: 'left'},
    {title: '编号', width: '80px', key: 'id', fixed: 'left'},
    {title: '头像', width: '50px', key: 'avatar', customSlot: 'avatar'},
    {title: '姓名', width: '80px', key: 'nickName'},
    {title: '手机号', width: '90px', key: 'mobile'},
    {title: '邮箱', width: '120px', key: 'email'},
    {title: '状态', width: '80px', key: 'status', customSlot: 'status'},
    {title: '签名', width: '260px', key: 'remark'},
    {title: '时间', width: '120px', key: 'createTime'},
    {
        title: '操作',
        width: '120px',
        customSlot: 'operator',
        key: 'operator',
        fixed: 'right'
    }
])

export const page = ref({
    pageNo: 1,
    pageSize: 10,
    total: '',
    layout: ['count', 'prev', 'page', 'next', 'limits', 'refresh', 'skip'],
})

export const queryForm = ref({
    pageNo: 1,
    pageSize: 10,
    account: '',
    nickName: '',
})

export const loading = ref(true);
export const edit = ref(false);
export const password = ref(false);
export const list = ref([])
export const title = ref("")

export const model = ref({
    id:'',
    nickName: '',
    account: '',
    password: '',
    password2:'',
    status: 1,
    remark: '',
    roleIds: [],
    mobile: '',
    email:'',
})


export const options = ref({
    label: '',
    value: '',
})

export const schema = reactive({
    nickName: {
        label: '姓名',
        type: 'input',
        props: {
            type: 'text',
            placeholder: '请输入姓名',
        }
    },
    account: {
        label: '账号',
        type: 'input',
        props: {
            type: 'text',
            placeholder: '请输入账号',
        }
    },
    password: {
        label: '密码',
        type: 'input',
        hidden: false,
        props: {
            autocomplete: "off",
            type: 'password',
            placeholder: '请输入密码',
        },
    },
    mobile: {
        label: '手机号',
        type: 'input',
        props: {
            type: 'text',
            placeholder: '请输入手机号',
        }
    },
    email: {
        label: '邮箱',
        type: 'input',
        props: {
            type: 'text',
            placeholder: '请输入邮箱',
        }
    },
    remark: {
        label: '描述',
        type: 'textarea',
        props: {
            placeholder: '请输入描述',
        }
    },
    roleIds: {
        label: '角色',
        type: 'select',
        props: {
            multiple: true,
            options: options,
        }
    },
})

export const passwordSchema = reactive({
    account: {
        label: '账号',
        type: 'input',
        props: {
            type: 'text',
            readonly: true,
        }
    },
    password: {
        label: '新密码',
        type: 'input',
        props: {
            autocomplete: "off",
            type: 'password',
            placeholder: '请输入密码',
        }
    },
    password2: {
        label: '确认密码',
        type: 'input',
        props: {
            autocomplete: "off",
            type: 'password',
            placeholder: '请输入密码',
        }
    },
})