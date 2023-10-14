package com.zhou.domain.entity;

import java.util.Date;

import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
/**
 * 评论表(Comment)表实体类
 *
 * @author makejava
 * @since 2023-10-13 15:32:37
 */
@SuppressWarnings("serial")
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("sg_comment")
public class Comment  {
    @TableId
    private Long id;

    //评论类型（0代表文章评论，1代表友链评论）
    private String type;
    //文章id
    private Long articleId;
    //根评论id
    private Long rootId;
    //评论内容
    private String content;
    //所回复的目标评论的userid
    private Long toCommentUserId;
    //回复目标评论id
    private Long toCommentId;

    //由于我们在MyMetaObjectHandler类配置了mybatisplus的字段自动填充
    //所以就能指定哪个字段在什么时候进行自动填充，例如该类其它字段新增了数据，那么createBy字段就会自动填充值
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;

    //由于我们在MyMetaObjectHandler类配置了mybatisplus的字段自动填充
    //所以就能指定哪个字段在什么时候进行自动填充，例如该类其它字段新增了数据，那么createTime字段就会自动填充值
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;

    //由于我们在MyMetaObjectHandler类配置了mybatisplus的字段自动填充
    //所以就能指定哪个字段在什么时候进行自动填充，例如该类其它字段新增或更新了数据，那么createBy字段就会自动填充值
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateBy;

    //由于我们在MyMetaObjectHandler类配置了mybatisplus的字段自动填充
    //所以就能指定哪个字段在什么时候进行自动填充，例如该类其它字段新增或更新了数据，那么updateTime字段就会自动填充值
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    //删除标志（0代表未删除，1代表已删除）
    private Integer delFlag;
    
}
