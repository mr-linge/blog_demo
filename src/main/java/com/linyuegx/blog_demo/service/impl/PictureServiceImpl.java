/**
 * Copyright (C), 2015-2020, LSTAR
 * FileName: PictureServiceImpl
 * Author:   OneStar
 * Date:     2020/3/20 9:35
 * Description: 照片墙服务层接口实现类
 * History:
 * <author>          <time>          <version>          <desc>
 * oneStar           修改时间           版本号              描述
 */
package com.linyuegx.blog_demo.service.impl;

import com.linyuegx.blog_demo.NotFoundException;
import com.linyuegx.blog_demo.dao.PictureRepository;
import com.linyuegx.blog_demo.po.Comment;
import com.linyuegx.blog_demo.po.Picture;
import com.linyuegx.blog_demo.service.PictureService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * 〈一句话功能简述〉<br> 
 * 〈照片墙服务层接口实现类〉
 *
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 * @create 2020/3/20
 * @since 1.0.0
 */
@Service
public class PictureServiceImpl implements PictureService {

    @Autowired
    private PictureRepository pictureRepository;

    @Transactional
    @Override
    public List<Picture> listPicture() {
        Sort sort = Sort.by(Sort.Direction.DESC,"id");
        return pictureRepository.findAll(sort);

    }

    @Transactional
    @Override
    public Picture savePicture(Picture picture) {
        return pictureRepository.save(picture);
    }

    @Transactional
    @Override
    public Picture getPicture(Long id) {
        Optional<Picture> byId = pictureRepository.findById(id);
        Picture t = byId.isPresent()?byId.get() : null;
        return t ;
    }

    @Override
    public Page<Picture> listPicture(Pageable pageable) {
        return pictureRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Picture updatePicture(Long id, Picture picture) {
        Optional<Picture> byId = pictureRepository.findById(id);
        Picture P = byId.isPresent()?byId.get() : null;
        if(P == null){
            throw new NotFoundException("不存在该照片");
        }
        BeanUtils.copyProperties(picture,P);
        return pictureRepository.save(P);
    }

    @Transactional
    @Override
    public void deletePicture(Long id) {
        pictureRepository.deleteById(id);
    }
}