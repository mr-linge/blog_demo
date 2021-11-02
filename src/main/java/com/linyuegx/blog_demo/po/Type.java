package com.linyuegx.blog_demo.po;


import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;

/**
 * Create by lin on  2021/10/23 17:45
 */

@Entity
@Table(name = ("t_type"))
public class Type {
    @Id
    @GeneratedValue
    private Long id;

    @NotBlank(message ="分类名称不能为空")
    private  String name;

    // 表关系
    @OneToMany(mappedBy = ("type"))
    private List<Blog> blog_list = new ArrayList<Blog>();
    public Type() {
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

    public List<Blog> getBlog_list() {
        return blog_list;
    }

    public void setBlog_list(List<Blog> blog_list) {
        this.blog_list = blog_list;
    }

    @Override
    public String toString() {
        return "Type{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
