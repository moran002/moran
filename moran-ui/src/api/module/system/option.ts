import Http from '../../http';

//列表
export const getList = function (data: any) {
    return Http.get('/system/log/list', data)
}
