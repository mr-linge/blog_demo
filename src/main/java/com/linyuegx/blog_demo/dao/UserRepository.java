package com.linyuegx.blog_demo.dao;

import com.linyuegx.blog_demo.po.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository  extends JpaRepository<User,Long>{

    User findAllByUsernameAndPassword(String username,String password);

}
