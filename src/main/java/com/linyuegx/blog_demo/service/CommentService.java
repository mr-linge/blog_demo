package com.linyuegx.blog_demo.service;


import com.linyuegx.blog_demo.po.Comment;

import java.util.List;

/**
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 */
public interface CommentService {

    List<Comment> listCommentByBlogId(Long blogId);

    Comment saveComment(Comment comment);

    List<Comment> listComment();
}
