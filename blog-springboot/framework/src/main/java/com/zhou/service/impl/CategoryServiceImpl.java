package com.zhou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.constants.SystemCanstants;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Article;
import com.zhou.domain.entity.Category;
import com.zhou.domain.vo.CategoryVo;
import com.zhou.mapper.CategoryMapper;
import com.zhou.service.ArticleService;
import com.zhou.service.CategoryService;
import com.zhou.utils.BeanCopyUtils;
import com.zhou.domain.vo.HotCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 分类表(Category)表服务实现类
 *
 * @author makejava
 * @since 2023-10-04 16:31:52
 */
@Service("categoryService")
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {
@Autowired
  private   ArticleService articleService;

    @Override
    public ResponseResult getCategoryList() {
       //先查询文章表   得到所有的分类名  并去重
        LambdaQueryWrapper<Article>articleWrapper=new LambdaQueryWrapper<>();
        articleWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        List<Article> articlelist = articleService.list(articleWrapper);
        List<Long> categoryid = articlelist.stream()
                .map(c -> c.getCategoryId())
                .distinct()
                .collect(Collectors.toList());
        //然后判断   分类名是否都是允许的不是禁用的  查询分类表
        List<Category> categories = listByIds(categoryid);
        List<Category> categorylist = categories.stream()
                .filter(category -> SystemCanstants.STATUS_NORMAL.equals(category.getStatus()))
                .collect(Collectors.toList());
        //封装vo
        List<HotCategoryVO> hotCategoryVOS = BeanCopyUtils.copyBeanList(categorylist, HotCategoryVO.class);
        return ResponseResult.okResult(hotCategoryVOS);
    }


    //----------------------------写博客-查询文章分类的接口--------------------------------------

    @Override
    public List<CategoryVo> listAllCategory() {
        LambdaQueryWrapper<Category> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Category::getStatus, SystemCanstants.NORMAL);
        List<Category> list = list(wrapper);
        List<CategoryVo> categoryVos = BeanCopyUtils.copyBeanList(list, CategoryVo.class);
        return categoryVos;
    }

}
