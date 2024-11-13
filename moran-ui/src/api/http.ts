import axios, {AxiosRequestHeaders, AxiosResponse, InternalAxiosRequestConfig} from 'axios';
import {useUserStore} from "@/store/user";
import {layer} from '@layui/layui-vue';
import router from '../router'

const CODE_MESSAGE: any = {
    200: '服务器成功返回请求数据',
    201: '新建或修改数据成功',
    202: '一个请求已经进入后台排队(异步任务)',
    204: '删除数据成功',
    400: '发出信息有误',
    401: '用户没有权限(令牌失效、用户名、密码错误、登录过期)',
    402: '令牌过期',
    403: '用户得到授权，但是访问是被禁止的',
    404: '访问资源不存在',
    406: '请求格式不可得',
    410: '请求资源被永久删除，且不会被看到',
    500: '服务器发生错误',
    502: '网关错误',
    503: '服务不可用，服务器暂时过载或维护',
    504: '网关超时',
}

type TAxiosOption = {
    timeout: number;
    baseURL: string;
}

const config: TAxiosOption = {
    timeout: 5000,
    baseURL: import.meta.env.VITE_BASE_URL
}

class Http {
    service;

    constructor(config: TAxiosOption) {
        this.service = axios.create(config)

        /* 请求拦截 */
        this.service.interceptors.request.use((config: InternalAxiosRequestConfig) => {
            const userInfoStore = useUserStore();
            if (userInfoStore.token) {
                (config.headers as AxiosRequestHeaders).Authorization = 'Bearer ' + userInfoStore.token as string
            } else {
                if (router.currentRoute.value.path !== '/login') {
                    router.push('/login');
                }
            }
            return config
        }, error => {
            return Promise.reject(error);
        })

        const handleData = async (response: any) => {
            switch (response.data.code) {
                case 200:
                    return response.data;
                case 401:
                    await router.push({path: '/login', replace: true})
                    break
            }
            const errMsg = `${
                response.data && response.data.msg
                    ? response.data.msg
                    : CODE_MESSAGE[response.data.code]
                        ? CODE_MESSAGE[response.data.code]
                        : response.statusText
            }`
            layer.msg(errMsg, {icon: 2})
            return Promise.reject(response);
        }

        /* 响应拦截 */
        this.service.interceptors.response.use(
            (response: AxiosResponse<any>) => handleData(response),
            (error) => {
                const {response} = error
                if (response === undefined) {
                    layer.msg("api请求超时", {icon: 2})
                    return {};
                } else {
                    return handleData(response)
                }
            })
    }

    /* GET 方法 */
    get<T>(url: string, params?: object, _object = {}): Promise<any> {
        return this.service.get(url, {params, ..._object})
    }

    /* POST 方法 */
    post<T>(url: string, params?: object, _object = {}): Promise<any> {
        return this.service.post(url, params, _object)
    }

    /* PUT 方法 */
    put<T>(url: string, params?: object, _object = {}): Promise<any> {
        return this.service.put(url, params, _object)
    }

    /* DELETE 方法 */
    delete<T>(url: string, params?: any, _object = {}): Promise<any> {
        return this.service.delete(url, {params, ..._object})
    }
}

export default new Http(config)