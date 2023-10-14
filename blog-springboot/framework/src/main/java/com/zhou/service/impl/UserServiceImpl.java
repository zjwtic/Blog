package com.zhou.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.User;
import com.zhou.domain.vo.UserInfoVo;
import com.zhou.mapper.UserMapper;
import com.zhou.service.UserService;
import com.zhou.utils.BeanCopyUtils;
import com.zhou.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(SysUser)表服务实现类
 *
 * @author makejava
 * @since 2023-10-13 23:18:12
 */
@Service("sysUserService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    //查询个人信息
    public ResponseResult userInfo() {

        //获取当前用户的用户id。SecurityUtils是我们在huanf-framework工程写的类
        Long userId = SecurityUtils.getUserId();

        //根据用户id查询用户信息
        User user = getById(userId);

        //封装成UserInfoVo(我们在huanf-framework工程写的类)，然后返回
        UserInfoVo vo = BeanCopyUtils.copyBean(user,UserInfoVo.class);
        return ResponseResult.okResult(vo);
    }
}
