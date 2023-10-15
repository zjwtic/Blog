package com.zhou.controller;

import com.zhou.annotation.mySystemlog;
import com.zhou.domain.ResponseResult;
import com.zhou.service.LinkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/link")
public class LinkController {
    @Autowired
    private LinkService linkService;
  @GetMapping("getAllLink")
  @mySystemlog(xxbusinessName = "得到所有友链")
    public ResponseResult getAllLink(){
     return linkService.getAllLink();
    }

}
