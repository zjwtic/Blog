package com.zhou.controller;

import com.zhou.annotation.mySystemlog;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Link;
import com.zhou.domain.entity.User;
import com.zhou.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 35238
 * @date 2023/7/27 0027 15:18
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    //UserService是我们在huanf-framework工程写的接口
    private UserService userService;

    @GetMapping("/userInfo")
    @mySystemlog(xxbusinessName = "查询个人信息")
    public ResponseResult userInfo(){
        return userService.userInfo();
    }

    @PutMapping("userInfo")
    @mySystemlog(xxbusinessName = "更新个人信息")
    public ResponseResult  updateUserInfo(@RequestBody User user){
        //更新个人信息
        return userService.updateUserInfo(user);
    }

    @PostMapping("/register")
    @mySystemlog(xxbusinessName = "用户注册")
    public ResponseResult register(@RequestBody User user){
        //注册功能
        return userService.register(user);
    }

}