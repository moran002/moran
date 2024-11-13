import BasicLayout from '@/layouts/BasicLayout.vue';
import Login from '@/views/login/index.vue';


export default [
    {
        path: '/',
        redirect: '/workSpace'
    }, {
        path: '/login',
        component: Login,
        meta: {title: '登录页面'},
    }, {
        path: '/workspace',
        redirect: '/workspace/workbench',
        component: BasicLayout,
        meta: {title: '首页'},
        children: [
            {
                path: '/workspace/workbench',
                name: 'Workbench',
                component: () => import('@/views/workSpace/workbench/index.vue'),
                meta: {title: '首页', requireAuth: true},
            }
        ]
    }, {
        path: '/system',
        component: BasicLayout,
        meta: {title: '系统管理'},
        children: [
            {
                path: '/system/user',
                component: () => import('@/views/system/user/index.vue'),
                meta: {title: '用户管理', requireAuth: true},
            },
            {
                path: '/system/role',
                component: () => import('@/views/system/role/index.vue'),
                meta: {title: '角色管理', requireAuth: true},
            },
            {
                path: '/system/menu',
                component: () => import('@/views/system/menu/index.vue'),
                meta: {title: '菜单管理', requireAuth: true},
            },
            {
                path: '/system/option',
                component: () => import('@/views/system/option/index.vue'),
                meta: {title: '操作日志', requireAuth: true},
            },
        ]
    }


]
