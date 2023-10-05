package com.zhou.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zhou.constants.SystemCanstants;
import com.zhou.domain.ResponseResult;
import com.zhou.domain.entity.Link;
import com.zhou.domain.vo.LinkVO;
import com.zhou.mapper.LinkMapper;
import com.zhou.service.LinkService;
import com.zhou.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2023-10-05 15:32:37
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    @Override
    public ResponseResult getAllLink() {
        LambdaQueryWrapper<Link>queryWrapper=new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemCanstants.LINK_STATUS_NORMAL);
        List<Link> linklist = list(queryWrapper);
        List<LinkVO> linkVOS = BeanCopyUtils.copyBeanList(linklist, LinkVO.class);
        return ResponseResult.okResult(linkVOS);
    }
}
