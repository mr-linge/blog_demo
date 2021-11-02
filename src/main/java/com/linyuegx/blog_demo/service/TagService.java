package com.linyuegx.blog_demo.service;

import com.linyuegx.blog_demo.po.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TagService {
    Tag  saveType(Tag tag);

    Tag  getType(Long id);

    Page<Tag> listType(Pageable pageable);

    Tag  updateType(Long id,Tag tag);

    Tag  getTypeByname(String name);

    List<Tag> listTag();

    List<Tag> listTag(String id);

    List<Tag> listTag(Integer size);

    void deleteType(Long id);
}
