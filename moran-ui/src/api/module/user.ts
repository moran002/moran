import Http from '../http';

//登录验证码
export const captcha = function () {
    return Http.get('/auth/captcha')
}

export const login = function(loginForm: any) {
    return Http.post('/auth/login', loginForm)
}



