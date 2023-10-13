package com.zhou.service;


import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.User;

/**
 * @author 35238
 * @date 2023/7/22 0022 21:38
 */
public interface BlogLoginService {
    ResponseResult login(User user);

    ResponseResult logout();
}