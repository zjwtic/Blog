package com.zhou.controller;

import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Article;
import com.zhou.domain.vo.UpdateArticleVO;
import com.zhou.dto.AddArticleDto;
import com.zhou.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 35238
 * @date 2023/8/7 0007 15:21
 */
@RestController
@RequestMapping("/content/article")
public class ArticleController {

    //------------------------------新增博客文章-----------------------------

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public ResponseResult add(@RequestBody AddArticleDto article){
        return articleService.add(article);
    }

    @GetMapping("list")
    public ResponseResult list(Article article, Integer pageNum, Integer pageSize){
return articleService.pagelist(article,pageNum,pageSize);
    }

    @GetMapping("/{id}")
    public ResponseResult getContentById(@PathVariable("id") Long id){

        return  articleService.getNeedUpdateById(id);
    }

    @PutMapping
    public ResponseResult update(@RequestBody UpdateArticleVO updateArticleVO){
    return     articleService.updatebymessage(updateArticleVO);
    }

    @DeleteMapping("/{id}")
    public ResponseResult delete(@PathVariable Long id){
        articleService.removeById(id);
        return ResponseResult.okResult();
    }


}