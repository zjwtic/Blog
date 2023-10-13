package com.zhou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.constants.SystemCanstants;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Article;
import com.zhou.domain.entity.Category;
import com.zhou.domain.vo.ArticleDetailVO;
import com.zhou.domain.vo.PageVO;
import com.zhou.domain.vo.ShowArticleVO;
import com.zhou.mapper.ArticleMapper;
import com.zhou.mapper.CategoryMapper;
import com.zhou.service.ArticleService;
import com.zhou.service.CategoryService;
import com.zhou.utils.BeanCopyUtils;
import com.zhou.domain.vo.HotArticleVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ArticleServiceImpl extends ServiceImpl<ArticleMapper, Article> implements ArticleService {
 @Autowired
 private    CategoryService categoryService;
    @Override
    public ResponseResult hotArticleList() {
        LambdaQueryWrapper<Article>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        queryWrapper.orderByDesc(Article::getViewCount);
        Page<Article>page=new Page<>(SystemCanstants.ARTICLE_STATUS_CURRENT,SystemCanstants.ARTICLE_STATUS_SIZE);
     page(page,queryWrapper);
        List<Article> records = page.getRecords();
        //解决: 把结果封装在HotArticleVo实体类里面，在HotArticleVo实体类只写我们要的字段
//        List<HotArticleVO> articleVos = new ArrayList<>();
//        for (Article xxarticle : records) {
//            HotArticleVO xxvo = new HotArticleVO();
//            //使用spring提供的BeanUtils类，来实现bean拷贝。第一个参数是源数据，第二个参数是目标数据，把源数据拷贝给目标数据
//            //虽然xxarticle里面有很多不同的字段，但是xxvo里面只有3个字段(没有具体数据)，所以拷贝之后，就能把xxvo里面的三个字段填充具体数据
//            BeanUtils.copyProperties(xxarticle,xxvo); //xxarticle就是Article实体类，xxvo就是HotArticleVo实体类
//            //把我们要的数据(也就是上一行的xxvo)封装成集合
//            articleVos.add(xxvo);
//        }
        List<HotArticleVO> articleVos = BeanCopyUtils.copyBeanList(records, HotArticleVO.class);

        return ResponseResult.okResult(articleVos);
    }

    @Override
    public ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId) {
        //查询条件
        LambdaQueryWrapper<Article>queryWrapper=new LambdaQueryWrapper<>();
        // 先得到所有的已发布的 文章
        queryWrapper.eq(Article::getStatus, SystemCanstants.ARTICLE_STATUS_NORMAL);
        // 根据 ontop  进行  降序排序
        queryWrapper.orderByDesc(Article::getIsTop);
        //   如果 categoryid 存在   找到对于的文章
//        if(categoryId!=null&&categoryId>0){
//            queryWrapper.eq(Article::getCategoryId,categoryId);
//        }
        queryWrapper.eq(Objects.nonNull(categoryId)&&categoryId>0,Article::getCategoryId,categoryId);
        //进行分页查询
        Page<Article>page=new Page<>(pageNum,pageSize);//指定分页 数量
        page(page,queryWrapper);//基于条件进行分页查询
        List<Article> records = page.getRecords();

        // 进行  设置categoryname
//        for (Article record : records) {
//            Category Id = categoryService.getById(record.getCategoryId());
//            record.setCategoryName(Id.getName());
//        }
        records = records.stream()
                .map(article ->  article.setCategoryName( categoryService.getById(article.getCategoryId()).getName()))
                .collect(Collectors.toList());
        //vo 优化
        List<ShowArticleVO> showArticleVOS = BeanCopyUtils.copyBeanList(records, ShowArticleVO.class);
        //返回
        PageVO pageVo = new PageVO(showArticleVOS,page.getTotal());
        return ResponseResult.okResult(pageVo);


    }

    @Override
    public ResponseResult getContentById(Long id) {
    // 先通过id 得到  对应的文章内容
        Article article = getById(id);
        // vo优化
        ArticleDetailVO articleDetailVO = BeanCopyUtils.copyBean(article, ArticleDetailVO.class);
        //设置里面的 分类名字
        Long categoryId = articleDetailVO.getCategoryId();
        Category category = categoryService.getById(categoryId);
if(category!=null){
    articleDetailVO.setCategoryName(category.getName());
}
//返回
return ResponseResult.okResult(articleDetailVO);

    }
}
