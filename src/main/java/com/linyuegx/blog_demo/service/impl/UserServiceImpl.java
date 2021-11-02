package com.linyuegx.blog_demo.service.impl;

import com.linyuegx.blog_demo.dao.UserRepository;
import com.linyuegx.blog_demo.po.User;
import com.linyuegx.blog_demo.service.UserService;
import com.linyuegx.blog_demo.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Create by lin on  2021/10/23 18:42
 */

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User checkUser(String username, String password) {
        return userRepository.findAllByUsernameAndPassword(username, MD5Utils.code(password));
    }
}
