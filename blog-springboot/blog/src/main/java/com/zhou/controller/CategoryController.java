package com.zhou.controller;

import com.zhou.annotation.mySystemlog;
import com.zhou.domain.ResponseResult;
import com.zhou.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

@GetMapping("/getCategoryList")
@mySystemlog(xxbusinessName = "查看分类文章信息")
    public ResponseResult getCategoryList(){
   ResponseResult result=categoryService.getCategoryList();
   return result;
    }
}
