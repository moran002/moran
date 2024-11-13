import Http from '../../http';

//列表
export const getList = function (data: any) {
    return Http.get('/system/role/list', data)
}
//更新
export const update = function(data: any) {
    return Http.post('/system/role/update', data)
}
//删除
export const del = function(data: any) {
    return Http.post('/system/role/del', data)
}

//更新权限
export const updateMenus = function(data: any) {
    return Http.post('/system/role/menus', data)
}