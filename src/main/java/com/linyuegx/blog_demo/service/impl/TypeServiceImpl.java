package com.linyuegx.blog_demo.service.impl;

import com.linyuegx.blog_demo.NotFoundException;
import com.linyuegx.blog_demo.dao.TypeRespository;
import com.linyuegx.blog_demo.po.Type;
import com.linyuegx.blog_demo.service.TypeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * Create by lin on  2021/10/26 0:48
 */
@Service
public class TypeServiceImpl implements TypeService {
    @Autowired
    private TypeRespository typeRespository;

    @Transactional
    @Override
    public Type saveType(Type type) {
        return typeRespository.save(type);
    }
    @Transactional
    @Override
    public Type getType(Long id) {
        Optional<Type> result = typeRespository.findById(id);
        return result.isPresent()?result.get():null;
    }
    @Transactional
    @Override
    public Page<Type> listType(Pageable pageable) {
        return typeRespository.findAll(pageable);
    }
    @Transactional
    @Override
    public Type updateType(Long id, Type type) {
        Optional<Type> result = typeRespository.findById(id);
        Type t = result.isPresent() ? result.get() : null;
        if(t==null){
            throw new NotFoundException();
        }
        BeanUtils.copyProperties(type,t);
        return typeRespository.save(t);
    }

    @Override
    public Type getTypeByname(String name) {
        return typeRespository.findByName(name);
    }

    @Override
    public List<Type> listtype() {
        return typeRespository.findAll();
    }

    @Override
    public List<Type> listTypeTop(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"blog_list.size");
        Pageable pageable = PageRequest.of(0,size,sort);
        return typeRespository.findTop(pageable);
    }

    @Transactional
    @Override
    public void deleteType(Long id) {
        typeRespository.deleteById(id);
    }
}
