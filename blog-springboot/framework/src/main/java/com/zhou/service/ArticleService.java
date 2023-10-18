package com.zhou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Article;
import com.zhou.domain.vo.UpdateArticleVO;
import com.zhou.dto.AddArticleDto;

public interface ArticleService extends IService<Article> {
    ResponseResult hotArticleList();

    ResponseResult articleList(Integer pageNum, Integer pageSize, Long categoryId);

    ResponseResult getContentById(Long id);

    ResponseResult updateViewCount(Long id);

    //新增博客文章
    ResponseResult add(AddArticleDto article);

    ResponseResult pagelist(Article article, Integer pageNum, Integer pageSize);

    ResponseResult getNeedUpdateById(Long id);

    ResponseResult updatebymessage(UpdateArticleVO updateArticleVO);
}
