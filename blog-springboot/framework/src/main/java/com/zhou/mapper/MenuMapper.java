package com.zhou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-16 22:47:51
 */
public interface MenuMapper extends BaseMapper<Menu> {

    List<String> selectPermsByOtherUserId(Long id);

    List<Menu> selectAllRouterMenu();
    //查询普通用户的路由信息(权限菜单)
    List<Menu> selectOtherRouterMenuTreeByUserId(Long userId);

    List<Long> selectMenuListByRoleId(Long roleId);
}
