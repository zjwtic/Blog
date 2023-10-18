package com.zhou.controller;

import com.zhou.domain.entity.Menu;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.vo.MenuVo;
import com.zhou.domain.vo.UpdateArticleVO;
import com.zhou.service.MenuService;
import com.zhou.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author 35238
 * @date 2023/8/10 0010 10:54
 */
@RestController
@RequestMapping("/system/menu")
public class MenuController {

    @Autowired
    private MenuService menuService;

    //---------------------------------查询菜单列表--------------------------------------

    @GetMapping("/list")
    public ResponseResult list(Menu menu) {
        List<Menu> menus = menuService.selectMenuList(menu);
        List<MenuVo> menuVos = BeanCopyUtils.copyBeanList(menus, MenuVo.class);
        return ResponseResult.okResult(menuVos);
    }

    //-----------------------------------新增菜单---------------------------------------


    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getContentById(@PathVariable("id") Long id){

        return  menuService.getNeedUpdateById(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody Menu menu){
        if (menu.getId().equals(menu.getParentId())) {
            return ResponseResult.errorResult(500,"修改菜单'" + menu.getMenuName() + "'失败，上级菜单不能选择自己");
        }
        return     menuService.updatebymessage(menu);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
      return   menuService.removerootid(id);
    }
}