package com.zhou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Menu;

import java.util.List;


/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-10-16 22:47:51
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    //查询用户的路由信息，也就是查询权限菜单
    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<Menu> selectMenuList(Menu menu);

    ResponseResult getNeedUpdateById(Long id);

    ResponseResult updatebymessage(Menu menu);

    ResponseResult removerootid(Long id);
    //修改角色-根据角色id查询对应角色菜单列表树
    List<Long> selectMenuListByRoleId(Long roleId);
}
