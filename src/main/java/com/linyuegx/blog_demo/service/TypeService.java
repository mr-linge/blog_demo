package com.linyuegx.blog_demo.service;

import com.linyuegx.blog_demo.po.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TypeService {
    Type saveType(Type type);

    Type getType(Long id);

    Page<Type> listType(Pageable pageable);

    Type updateType(Long id,Type type);

    Type getTypeByname(String name);

    List<Type> listtype();

    List<Type> listTypeTop(Integer size);

    void deleteType(Long id);
}
