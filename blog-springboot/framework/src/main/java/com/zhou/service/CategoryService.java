package com.zhou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Category;


/**
 * 分类表(Category)表服务接口
 *
 * @author makejava
 * @since 2023-10-04 16:31:52
 */
public interface CategoryService extends IService<Category> {

    ResponseResult getCategoryList();
}
