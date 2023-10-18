package com.zhou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-10-16 22:47:34
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize);

    ResponseResult add(Role role);

    //修改角色-保存修改好的角色信息
    void updateRole(Role role);

    //新增用户-①查询角色列表接口
    List<Role> selectRoleAll();
}
