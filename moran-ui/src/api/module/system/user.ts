import Http from '../../http';

//列表
export const getList = function (data: any) {
    return Http.get('/system/user/list', data)
}
//更新
export const update = function(data: any) {
    return Http.post('/system/user/update', data)
}
//删除
export const del = function(data: any) {
    return Http.post('/system/user/del', data)
}

//状态
export const updateStatus = function(data: any) {
    return Http.post('/system/user/status', data)
}
