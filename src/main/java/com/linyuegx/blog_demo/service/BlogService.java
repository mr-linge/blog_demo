package com.linyuegx.blog_demo.service;

import com.linyuegx.blog_demo.po.Blog;
import com.linyuegx.blog_demo.vo.BlogQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface BlogService {
    Blog getBlog(Long id);

    Page<Blog> listBlog(Pageable pageable, BlogQuery blog);

    Page<Blog> listBlog(Pageable pageable);

    Page<Blog> listBlog(Pageable pageable,String query);

    Blog saveBlog(Blog blog);

    Blog updateBlog(Long id,Blog blog);

    void deleteBlog(Long id);

    List<Blog> listType(Integer size);

    Blog getAndConvert(Long id);

    Map<String,List<Blog>> archiveBlog();

    int countBlog();

}
