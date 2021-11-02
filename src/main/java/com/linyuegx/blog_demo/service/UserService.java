package com.linyuegx.blog_demo.service;

import com.linyuegx.blog_demo.po.User;

public interface UserService {
    User checkUser(String username,String password);
}
