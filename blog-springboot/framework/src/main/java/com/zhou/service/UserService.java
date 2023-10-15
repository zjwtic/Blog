package com.zhou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.User;

/**
 * @author 35238
 * @date 2023/7/25 0025 17:48
 */
public interface UserService extends IService<User> {

    //个人信息查询
    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);
}