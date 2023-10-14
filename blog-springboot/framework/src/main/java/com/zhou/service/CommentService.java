package com.zhou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2023-10-13 15:32:37
 */
public interface CommentService extends IService<Comment> {
    //查询评论区的评论。增加了commentType参数，用于区分文章的评论区、友链的评论区
    ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize);

    //在文章的评论区发送评论
    ResponseResult addComment(Comment comment);
}
