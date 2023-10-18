package com.zhou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.constants.SystemCanstants;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Role;
import com.zhou.domain.entity.RoleMenu;
import com.zhou.domain.vo.PageVO;
import com.zhou.mapper.RoleMapper;
import com.zhou.service.RoleMenuService;
import com.zhou.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 35238
 * @date 2023/8/4 0004 13:33
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {
    @Autowired
   private RoleMenuService roleMenuService;

    @Override
    //查询用户的角色信息
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员，如果是，就返回集合中只需要有admin
        if(id == 1L){
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }

        //否则查询对应普通用户所具有的的角色信息
        List<String> otherRole = getBaseMapper().selectRoleKeyByOtherUserId(id);

        return otherRole;
    }

    @Override
    public ResponseResult selectRolePage(Role role, Integer pageNum, Integer pageSize) {
        LambdaQueryWrapper<Role> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.like(StringUtils.hasText(role.getRoleName()),Role::getRoleName,role.getRoleName());
        lambdaQueryWrapper.eq(StringUtils.hasText(role.getStatus()),Role::getStatus,role.getStatus());
        lambdaQueryWrapper.orderByAsc(Role::getRoleSort);

        Page<Role> page = new Page<>();
        page.setCurrent(pageNum);
        page.setSize(pageSize);
        page(page,lambdaQueryWrapper);

        //转换成VO
        List<Role> roles = page.getRecords();

        PageVO pageVo = new PageVO();
        pageVo.setTotal(page.getTotal());
        pageVo.setRows(roles);
        return ResponseResult.okResult(pageVo);
    }

    @Override
    @Transactional
    public ResponseResult add(Role role) {
        save(role);//可以拿到返回来的id
        System.out.println(role.getId());
        if(role.getMenuIds()!=null&&role.getMenuIds().size()>0){
            List<RoleMenu> menus = role.getMenuIds()
                    .stream()
                    .map(menuid -> new RoleMenu(role.getId(), menuid))
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(menus);
        }
        return ResponseResult.okResult();
    }

    //-----------------------修改角色-保存修改好的角色信息----------------------------

    @Override
    public void updateRole(Role role) {
        updateById(role);
        roleMenuService.deleteRoleMenuByRoleId(role.getId());
        if(role.getMenuIds()!=null&&role.getMenuIds().size()>0){
            List<RoleMenu> menus = role.getMenuIds()
                    .stream()
                    .map(menuid -> new RoleMenu(role.getId(), menuid))
                    .collect(Collectors.toList());
            roleMenuService.saveBatch(menus);
        }
    }

    //-----------------------新增用户-①查询角色列表接口----------------------------

    @Override
    public List<Role> selectRoleAll() {
        return list(Wrappers.<Role>lambdaQuery().eq(Role::getStatus, SystemCanstants.NORMAL));

    }



}