package com.taotao.sso.service;

import com.taotao.pojo.TaotaoResult;
import com.taotao.pojo.TbUser;

public interface UserService {
    /**
     * 校验数据的方法
     * @return
     * @param param 需要校验的数据
     *
     * @param type  1用户名，2手机号码，3 邮箱地址
     *        return  true数据可用，false数据不可用
     */
    TaotaoResult getCheckDataResult(String param, Integer type);

    /**
     * 用户注册方法
     * @param tbUser 用户信息，注意需要校验信息
     * @return 200 成功，500 失败
     */
    TaotaoResult addUser(TbUser tbUser);

    /**
     * 登录方法
     * @param userName 用户账号
     * @param passWord 用户密码，加密验证
     * @return token是一个随机字符串
     */
    TaotaoResult getToken(String userName, String passWord);

    /**
     * 根据token查询用户信息
     * @param token
     * @return 用户信息的json字符串
     */
    TaotaoResult getUserByToken(String token);

    /**
     * 删除token来退出登录状态
     * @param token
     * @return
     */
    TaotaoResult deleteToken(String token);
}
