package com.zhou.service;

import com.zhou.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 35238
 * @date 2023/8/9 0009 13:40
 */

@Service("ps")
public class PermissionService {

    /**
     * 判断当前用户是否具有permission
     * @param permission 要判断的权限
     * @return
     */
    public boolean hasPermission(String permission){
        //如果是超级管理员  直接返回true
        if(SecurityUtils.isAdmin()){
            return true;
        }

        //否则  获取当前登录用户所具有的权限列表 如何判断是否存在permission
        // 这个permission其实就是sys_menu表的perms字段的值
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        //contains方法是 'List集合官方' 提供的方法，返回值是布尔值，如果用户具有对应权限就返回true
        return permissions.contains(permission);
    }
}