package com.zhou.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Link;
import com.zhou.domain.vo.PageVO;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2023-10-05 15:32:37
 */
public interface LinkService extends IService<Link> {

    ResponseResult getAllLink();

    //分页查询友链
    PageVO selectLinkPage(Link link, Integer pageNum, Integer pageSize);
}
