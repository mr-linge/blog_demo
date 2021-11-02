package com.linyuegx.blog_demo.service.impl;

import com.linyuegx.blog_demo.NotFoundException;
import com.linyuegx.blog_demo.dao.BlogResponsitory;
import com.linyuegx.blog_demo.po.Blog;
import com.linyuegx.blog_demo.po.Type;
import com.linyuegx.blog_demo.service.BlogService;
import com.linyuegx.blog_demo.utils.MarkdownUtils;
import com.linyuegx.blog_demo.utils.MyBeanUtils;
import com.linyuegx.blog_demo.vo.BlogQuery;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;
import java.util.*;

/**
 * Create by lin on  2021/10/26 14:09
 */
@Service
public class BlogServiceImpl implements BlogService {
    @Autowired
    private BlogResponsitory blogResponsitory;

    @Override
    public Blog getBlog(Long id) {
        Optional<Blog> result = blogResponsitory.findById(id);
        return result.isPresent()?result.get():null;
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, BlogQuery blog) {
        return blogResponsitory.findAll(new Specification<Blog>() {
            @Override
            public Predicate toPredicate(Root<Blog> root, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
                List<Predicate> Predicates = new ArrayList<>();
                if(!"".equals(blog.getTitle()) && blog.getTitle()!=null){
                    Predicates.add(criteriaBuilder.like(root.<String>get("title"),"%"+blog.getTitle()+"%"));
                }
                if(blog.getTypeId()!=null){
                    Predicates.add(criteriaBuilder.equal(root.<Type>get("type").get("id"),blog.getTypeId()));
                }
                if(blog.isRecommend()){
                    Predicates.add(criteriaBuilder.equal(root.<Boolean>get("recommend"),blog.isRecommend()));
                }
                query.where(Predicates.toArray(new Predicate[Predicates.size()]));
                return null;
            }
        },pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable) {
        return blogResponsitory.findAll(pageable);
    }

    @Override
    public Page<Blog> listBlog(Pageable pageable, String query) {
        return blogResponsitory.findbyquery(query,pageable);
    }

    @Transactional
    @Override
    public Blog saveBlog(Blog blog) {
        if(blog.getId()==null){
            blog.setCreateTime(new Date());
            blog.setUpdateTime(new Date());
            blog.setViews(0);
        }else {
            blog.setUpdateTime(new Date());
        }

        return blogResponsitory.save(blog);
    }
    @Transactional
    @Override
    public Blog updateBlog(Long id, Blog blog) {
        Optional<Blog> result = blogResponsitory.findById(id);
        Blog b = result.isPresent() ? result.get() : null;
        if(b==null){
            throw new NotFoundException("该博客不存在");
        }
        BeanUtils.copyProperties(blog,b, MyBeanUtils.getNullPropertyNames(blog));
        b.setUpdateTime(new Date());
        return blogResponsitory.save(b);
    }
    @Transactional
    @Override
    public void deleteBlog(Long id) {
        blogResponsitory.deleteById(id);
    }

    @Override
    public List<Blog> listType(Integer size) {
        Sort sort = Sort.by(Sort.Direction.DESC,"updateTime");
        Pageable pageable =PageRequest.of(0,size,sort);
        return blogResponsitory.findTop(pageable);
    }

    @Override
    public Blog getAndConvert(Long id) {
        Optional<Blog> result = blogResponsitory.findById(id);
        Blog blog = result.isPresent() ? result.get() : null;
        if(blog==null){
            throw  new NotFoundException("该博客不存在");
        }
        Blog b = new Blog();
        BeanUtils.copyProperties(blog,b);
        String content = b.getContent();
        String s = MarkdownUtils.markdownToHtmlExtensions(content);
        b.setContent(s);
        return b;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = blogResponsitory.findGroupYear();
        Map<String, List<Blog>> map = new HashMap<>();
        for (String year : years) {
            map.put(year, blogResponsitory.findByYear(year));
        }
        return map;
    }
    @Override
    public int countBlog() {
        List<Blog> all = blogResponsitory.findAll();
         int count = 0;
        for (Blog blog : all) {
            count+=blog.getViews();
        }
        return count;
    }
}
