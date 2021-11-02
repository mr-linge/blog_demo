package com.linyuegx.blog_demo.service.impl;

import com.linyuegx.blog_demo.NotFoundException;
import com.linyuegx.blog_demo.dao.TagResponsitory;
import com.linyuegx.blog_demo.po.Tag;
import com.linyuegx.blog_demo.service.TagService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * Create by lin on  2021/10/26 12:01
 */
@Service
public class TagServiceImpl implements TagService {
    @Autowired
    TagResponsitory tagResponsitory;

    @Override
    public Tag saveType(Tag tag) {
        return tagResponsitory.save(tag);
    }

    @Override
    public Tag getType(Long id) {
        Optional<Tag> result = tagResponsitory.findById(id);
        return result.isPresent()?result.get():null;
    }

    @Override
    public Page<Tag> listType(Pageable pageable) {
        return tagResponsitory.findAll(pageable);
    }

    @Override
    public Tag updateType(Long id, Tag tag) {
        Optional<Tag> result = tagResponsitory.findById(id);
        Tag t = result.isPresent()?result.get() : null;
        if(t==null){
            throw new NotFoundException();
        }
        BeanUtils.copyProperties(tag,t);
        return tagResponsitory.save(t);
    }

    @Override
    public Tag getTypeByname(String name) {
        return tagResponsitory.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagResponsitory.findAll();
    }

    @Override
    public List<Tag> listTag(String id) {
        return tagResponsitory.findAllById(convertToList(id));
    }

    @Override
    public List<Tag> listTag(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"Blogs.size");
        Pageable pageable =PageRequest.of(0,size,sort);
        return tagResponsitory.findTop(pageable);
    }

    private  List<Long> convertToList(String ids){
        List<Long> list = new ArrayList<>();
        if(!"".equals(ids) && ids!= null){
            String[] idarray = ids.split(",");
            for (int i = 0; i <idarray.length ; i++) {
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public void deleteType(Long id) {
        tagResponsitory.deleteById(id);
    }
}
