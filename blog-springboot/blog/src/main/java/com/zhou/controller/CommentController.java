package com.zhou.controller;


import com.zhou.annotation.mySystemlog;
import com.zhou.constants.SystemCanstants;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Comment;
import com.zhou.dto.addCommentDto;
import com.zhou.service.CommentService;
import com.zhou.utils.BeanCopyUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 35238
 * @date 2023/7/25 0025 13:14
 */
@RestController
@RequestMapping("/comment")
@Api(tags = "评论接口",description = "基于评论的一些实现")
public class CommentController {

    @Autowired
    //CommentService是我们在huanf-framework工程写的类
    private CommentService commentService;

    @GetMapping("commentList")
    @mySystemlog(xxbusinessName = "查看文章评论信息")
    //ResponseResult是我们在huanf-framework工程写的类
    public ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize){
        return commentService.commentList(SystemCanstants.ARTICLE_COMMENT,articleId,pageNum,pageSize);
    }

    @PostMapping
    @mySystemlog(xxbusinessName = "增加评论信息")
    //在文章的评论区发送评论。ResponseResult是我们在huanf-framework工程写的类
    public ResponseResult addComment(@RequestBody addCommentDto addCommentDto){
        //把addCommentDto类转换为Comment类类型。BeanCopyUtils是我们在huanf-framework工程写的工具类，可以转换类的类型
        Comment comment = BeanCopyUtils.copyBean(addCommentDto, Comment.class);
        return commentService.addComment(comment);
    }

    @GetMapping("/linkCommentList")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNum",value = "页号"),
            @ApiImplicitParam(name = "pageSize",value = "每页大小")
    })
    @ApiOperation(value = "友链评论列表",notes = "获取友链评论区的评论")
    @mySystemlog(xxbusinessName = "查看友链评论信息")
    //在友链的评论区发送评论。ResponseResult是我们在huanf-framework工程写的类
    public ResponseResult linkCommentList(Integer pageNum,Integer pageSize){

        //commentService是我们刚刚实现文章的评论区发送评论功能时(当时用的是addComment方法，现在用commentList方法)，写的类
        //SystemCanstants是我们写的用来解决字面值的常量类，Article_LINK代表1
        return commentService.commentList(SystemCanstants.LINK_COMMENT,null,pageNum,pageSize);
    }
}