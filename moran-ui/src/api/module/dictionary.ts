import Http from '../http';

export const routers = function() {
  return Http.get('/dictionary/routers')
}

export const permission = function() {
  return Http.get('/dictionary/permissions')
}

export const logout = function() {
  return Http.post('/dictionary/logout')
}

export const roles = function() {
  return Http.get('/dictionary/roles')
}

export const getMenus = function() {
  return Http.get('/dictionary/menus')
}