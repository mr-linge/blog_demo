package com.linyuegx.blog_demo.po;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by lin on  2021/10/23 17:47
 */

@Entity
@Table(name = ("t_tag"))
public class
Tag {
    @Id
    @GeneratedValue
    private Long id;
    @NotBlank(message = "标签名字不能为空")
    private String name;

    @ManyToMany(mappedBy = ("tags"))
    private List<Blog> Blogs = new ArrayList<Blog>();

    public Tag() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Blog> getBlogs() {
        return Blogs;
    }

    public void setBlogs(List<Blog> blogs) {
        Blogs = blogs;
    }

    @Override
    public String toString() {
        return "Tag{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
