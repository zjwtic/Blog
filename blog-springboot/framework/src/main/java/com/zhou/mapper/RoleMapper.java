package com.zhou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.domain.entity.Role;

import java.util.List;


/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-16 22:47:34
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByOtherUserId(Long id);
}
