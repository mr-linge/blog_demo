/**
 * Copyright (C), 2015-2020, LSTAR
 * FileName: FriendLinkServiceImpl
 * Author:   OneStar
 * Date:     2020/3/12 21:37
 * Description: 友链接口实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * oneStar           修改时间           版本号              描述
 */
package com.linyuegx.blog_demo.service.impl;

import com.linyuegx.blog_demo.NotFoundException;
import com.linyuegx.blog_demo.dao.FriendLinkRespository;
import com.linyuegx.blog_demo.po.FriendLink;
import com.linyuegx.blog_demo.service.FriendLinkService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br> 
 * 〈友链接口实现类〉
 *
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 * @create 2020/3/12
 * @since 1.0.0
 */
@Service
public class FriendLinkServiceImpl implements FriendLinkService {

    @Autowired
    private FriendLinkRespository friendLinkRespository;

    @Transactional
    @Override
    public List<FriendLink> listFriendlinks() {
        return friendLinkRespository.findAll();
    }

    @Transactional
    @Override
    public FriendLink saveFriendLink(FriendLink friendLink) {
        return friendLinkRespository.save(friendLink);
    }

    @Transactional
    @Override
    public FriendLink getFriendLink(Long id) {
        Optional<FriendLink> byId = friendLinkRespository.findById(id);
        FriendLink friendLink = byId.isPresent() ? byId.get() : null;
        return friendLink;

    }

    @Transactional
    @Override
    public Page<FriendLink> listFriendLink(Pageable pageable) {
//        Sort sort = new Sort(Sort.Direction.DESC,"createTime");
        return friendLinkRespository.findAll(pageable);
    }

    @Transactional
    @Override
    public FriendLink updateFriendLink(Long id, FriendLink friendLink) {
        Optional<FriendLink> byId = friendLinkRespository.findById(id);
        FriendLink F= byId.isPresent() ? byId.get() : null;
        if(F == null){
            throw new NotFoundException("不存在该友链");
        }
        BeanUtils.copyProperties(friendLink,F);
        return friendLinkRespository.save(F);
    }

    @Transactional
    @Override
    public void deleteFriendLink(Long id) {
        friendLinkRespository.deleteById(id);
    }
}