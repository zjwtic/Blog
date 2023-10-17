package com.zhou.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zhou.domain.entity.Tag;
import io.lettuce.core.dynamic.annotation.Param;


/**
 * 标签(Tag)表数据库访问层
 *
 * @author makejava
 * @since 2023-10-16 19:28:05
 */
public interface TagMapper extends BaseMapper<Tag> {
}
