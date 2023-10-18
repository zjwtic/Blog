package com.zhou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.constants.SystemCanstants;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Role;
import com.zhou.domain.entity.User;
import com.zhou.domain.entity.UserRole;
import com.zhou.domain.vo.PageVO;
import com.zhou.domain.vo.UserInfoVo;
import com.zhou.domain.vo.UserShowVO;
import com.zhou.domain.vo.UserVo;
import com.zhou.enums.AppHttpCodeEnum;
import com.zhou.exception.SystemException;
import com.zhou.mapper.UserMapper;
import com.zhou.service.RoleService;
import com.zhou.service.UserRoleService;
import com.zhou.service.UserService;
import com.zhou.utils.BeanCopyUtils;
import com.zhou.utils.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

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


    @Override
    //更新个人信息
    public ResponseResult updateUserInfo(User user) {
        //updateById是mybatisplus提供的方法
        updateById(user);
        return ResponseResult.okResult();
    }

    @Autowired
    //解密和加密用的这套算法。我们在huanf-blog工程的securityConfig类里面覆盖过PasswordEncoder这个bean
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRoleService userRoleService;

    @Autowired
    private RoleService roleService;
    @Override
    //用户注册功能的具体代码
    public ResponseResult register(User user) {

        //对前端传过来的用户名进行非空判断，例如null、""，就抛出异常
        if(!StringUtils.hasText(user.getUserName())){
            //SystemException是我们写的异常类、AppHttpCodeEnum是我们写的枚举类
            throw new SystemException(AppHttpCodeEnum.USERNAME_NOT_NULL);
        }
        //密码
        if(!StringUtils.hasText(user.getPassword())){
            throw new SystemException(AppHttpCodeEnum.PASSWORD_NOT_NULL);
        }
        //邮箱
        if(!StringUtils.hasText(user.getEmail())){
            throw new SystemException(AppHttpCodeEnum.EMAIL_NOT_NULL);
        }
        //昵称
        if(!StringUtils.hasText(user.getNickName())){
            throw new SystemException(AppHttpCodeEnum.NICKNAME_NOT_NULL);
        }
//        //手机号码
//        if(!StringUtils.hasText(user.getPhonenumber())){
//            throw new SystemException(AppHttpCodeEnum.PHONENUMBER_NOT_NULL);
//        }

        //判断用户传给我们的用户名是否在数据库已经存在。userNameExist方法是下面定义的
        if(userNameExist(user.getUserName())){
            //SystemException是我们写的异常类、AppHttpCodeEnum是我们写的枚举类
            throw new SystemException(AppHttpCodeEnum.USERNAME_EXIST);
        }
        //判断用户传给我们的昵称是否在数据库已经存在。NickNameExist方法是下面定义的
        if(NickNameExist(user.getNickName())){
            //SystemException是我们写的异常类、AppHttpCodeEnum是我们写的枚举类
            throw new SystemException(AppHttpCodeEnum.NICKNAME_EXIST);
        }
        //判断用户传给我们的邮箱是否在数据库已经存在。NickNameExist方法是下面定义的
        if(EmailExist(user.getEmail())){
            //SystemException是我们写的异常类、AppHttpCodeEnum是我们写的枚举类
            throw new SystemException(AppHttpCodeEnum.EMAIL_EXIST);
        }

        //经过上面的判断，可以确保用户传给我们的用户名和昵称是数据库不存在的，且相关字段都不为空。就可以存入数据库
        //注意用户传给我们的密码是明文，对于密码我们要转成密文之后再存入数据库。注意加密要和解密用同一套算法
        //在huanf-blog工程的securityConfig类里面有解密算法，当时我们写了一个passwordEncoder方法，并且注入到了spring容器
        String encodePassword = passwordEncoder.encode(user.getPassword());//加密
        user.setPassword(encodePassword);
        //存入数据库。save方法是mybatisplus提供的方法
        save(user);

        //封装响应返回
        return ResponseResult.okResult();
    }



    //'判断用户传给我们的用户名是否在数据库已经存在' 的方法
    private boolean userNameExist(String userName) {
        //要知道是否存在，首先就是根据条件去数据库查
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //拿用户写的用户名跟数据库里面的用户名进行比较
        queryWrapper.eq(User::getUserName,userName);
        //如果大于0就说明从数据库查出来了，也就说明是已经存在数据库的
        boolean result = count(queryWrapper) > 0;
        //为true就说明已存在
        return result;
    }

    //'判断用户传给我们的昵称是否在数据库已经存在' 的方法
    private boolean NickNameExist(String nickName) {
        //要知道是否存在，首先就是根据条件去数据库查
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        //拿用户写的昵称跟数据库里面的昵称进行比较
        queryWrapper.eq(User::getNickName,nickName);
        //如果大于0就说明从数据库查出来了，也就说明是已经存在数据库的
        boolean result = count(queryWrapper) > 0;
        //为true就说明已存在
        return result;
    }

    //'判断用户传给我们的邮箱是否在数据库已经存在' 的方法
    private boolean EmailExist(String email) {
        //要知道是否存在，首先就是根据条件去数据库查
        LambdaQueryWrapper<User> queryWrapper;
        queryWrapper = new LambdaQueryWrapper<>();
        //拿用户写的昵称跟数据库里面的昵称进行比较
        queryWrapper.eq(User::getEmail,email);
        //如果大于0就说明从数据库查出来了，也就说明是已经存在数据库的
        boolean result = count(queryWrapper) > 0;
        //为true就说明已存在
        return result;

    }
    //--------------------------------查询用户列表-------------------------------------

    @Override
    public ResponseResult selectUserPage(User user, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper();

        queryWrapper.like(StringUtils.hasText(user.getUserName()),User::getUserName,user.getUserName());
        queryWrapper.eq(StringUtils.hasText(user.getStatus()),User::getStatus,user.getStatus());
        queryWrapper.eq(StringUtils.hasText(user.getPhonenumber()),User::getPhonenumber,user.getPhonenumber());

        Page<User> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,queryWrapper);

        //转换成VO
        List<User> users = page.getRecords();
//        List<UserVo> userVoList = users.stream()
//                .map(u -> BeanCopyUtils.copyBean(u, UserVo.class))
//                .collect(Collectors.toList());
        List<UserVo> userVoList = BeanCopyUtils.copyBeanList(users, UserVo.class);
        PageVO pageVo = new PageVO();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(userVoList);
        return ResponseResult.okResult(pageVo);
    }


    //-------------------------------新增用户-②新增用户--------------------------------



    @Override
    public boolean checkUserNameUnique(String userName) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getUserName,userName))==0;
    }

    @Override
    public boolean checkPhoneUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getPhonenumber,user.getPhonenumber()))==0;
    }

    @Override
    public boolean checkEmailUnique(User user) {
        return count(Wrappers.<User>lambdaQuery().eq(User::getEmail,user.getEmail()))==0;
    }

    @Override
    @Transactional
    public ResponseResult addUser(User user) {
        //密码加密处理
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        save(user);

        if(user.getRoleIds()!=null&&user.getRoleIds().size()>0){
            insertUserRole(user);
        }
        return ResponseResult.okResult();
    }
    private void insertUserRole(User user) {
        List<UserRole> sysUserRoles =user.getRoleIds().stream()
                .map(roleId -> new UserRole(user.getId(), roleId)).collect(Collectors.toList());
        userRoleService.saveBatch(sysUserRoles);
    }
    @Override
    public ResponseResult getusersbyid(Long id) {
        List<UserRole> userRoles = userRoleService.list(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId, id));
        List<Long> roleids = userRoles.stream()
                .map(userRole -> userRole.getRoleId())
                .collect(Collectors.toList());
        List<Role> roles = roleService.list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemCanstants.ROLE_STATUS));
        User u = getById(id);
        final UserVo user = BeanCopyUtils.copyBean(u, UserVo.class);
        UserShowVO userShowVO=new UserShowVO(roleids,roles,user);
        return ResponseResult.okResult(userShowVO);
    }

    @Override
    @Transactional
    public ResponseResult updateUser(User user) {
        updateUserInfo(user);
        userRoleService.remove(Wrappers.<UserRole>lambdaQuery().eq(UserRole::getUserId,user.getId()));

        List<UserRole> userRoles = user.getRoleIds()
                .stream()
                .map(r -> new UserRole(user.getId(), r))
                .collect(Collectors.toList());
      userRoleService.saveBatch(userRoles);

        return ResponseResult.okResult();
    }
}
