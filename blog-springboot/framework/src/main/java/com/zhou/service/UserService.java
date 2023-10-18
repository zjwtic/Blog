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


    //查询用户列表
    ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize);


    //增加用户-②新增用户
    boolean checkUserNameUnique(String userName);
    boolean checkPhoneUnique(User user);
    boolean checkEmailUnique(User user);
    ResponseResult addUser(User user);

    ResponseResult getusersbyid(Long id);

    ResponseResult updateUser(User user);
}