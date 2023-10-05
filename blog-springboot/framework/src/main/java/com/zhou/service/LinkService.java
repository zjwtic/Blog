package com.zhou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-10-05 15:32:37
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();
}
