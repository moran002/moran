import Http from '../../http';

//列表
export const getList = function (data: any) {
    return Http.get('/system/menu/list', data)
}
//更新
export const update = function(data: any) {
    return Http.post('/system/menu/update', data)
}
//删除
export const del = function(data: any) {
    return Http.post('/system/menu/del', data)
}