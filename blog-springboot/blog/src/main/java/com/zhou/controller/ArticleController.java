package com.zhou.controller;

import com.zhou.annotation.mySystemlog;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Article;
import com.zhou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
  @Autowired
  private   ArticleService articleService;

  @GetMapping("/list")
  @mySystemlog(xxbusinessName = "测试查看文章信息")
    public List<Article> test()
    {
    return articleService.list();
    }

    @GetMapping("/hotArticleList")
    @mySystemlog(xxbusinessName = "查看热评文章信息")
    public ResponseResult hotArticleList(){
      ResponseResult result=articleService.hotArticleList();
      return result;
    }

    @GetMapping("/articleList")
    @mySystemlog(xxbusinessName = "查看文章信息")
     public ResponseResult articleList(Integer pageNum,Integer pageSize,Long categoryId){
        return  articleService.articleList(pageNum,pageSize,categoryId);
    }
    @GetMapping("/{id}")
    @mySystemlog(xxbusinessName = "根据id查看文章信息")
  public ResponseResult getContentById(@PathVariable("id") Long id){

    return  articleService.getContentById(id);
    }

}
