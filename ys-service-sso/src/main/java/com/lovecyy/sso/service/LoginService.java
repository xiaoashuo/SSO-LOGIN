package com.lovecyy.sso.service;

import com.lovecyy.sso.common.dto.HpUser;

/**
 * @author ys
 * @topic 单点登录服务端
 * @date 2019/9/10 12:47
 */
public interface LoginService {
    /**
     * 登录用户
     * @param loginCode
     * @param plantPassword
     * @return
     */
    public HpUser login(String loginCode,String plantPassword);
}
