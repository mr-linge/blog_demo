package com.linyuegx.blog_demo.web.admin;

import com.linyuegx.blog_demo.po.Blog;
import com.linyuegx.blog_demo.po.User;
import com.linyuegx.blog_demo.service.*;
import com.linyuegx.blog_demo.vo.BlogQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

/**
 * Create by lin on  2021/10/26 0:03
 */
@Controller
@RequestMapping("/admin")
public class BlogsController {
    private static final String INPUT = "admin/blogs-input";
    private static final String LIST = "admin/blogs";
    private static final String REDIRECT_LIST = "redirect:/admin/blogs";

    @Autowired
    private BlogService blogService;
    @Autowired
    private TypeService typeService;
    @Autowired
    private TagService tagService;
    @Autowired
    MessageService messageService;
    @Autowired
    CommentService commentService;


    @GetMapping("/blogs")
    public  String blogs(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("types",typeService.listtype());
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        all(model);
        return "admin/blogs";
    }

    @PostMapping("/blogs/search")
    public  String search(@PageableDefault(size = 10,sort = {"updateTime"},direction = Sort.Direction.DESC) Pageable pageable, BlogQuery blog, Model model){
        model.addAttribute("page",blogService.listBlog(pageable,blog));
        all(model);
        return "admin/blogs :: blogList";
    }

    @GetMapping("/blogs/input")
    public String input(Model model,Pageable pageable){
        setTypeAndTag(model);
        model.addAttribute("blog",new Blog());
        model.addAttribute("page",blogService.listBlog(pageable));
        all(model);
        return "admin/blogs-input";
    }

    private  void setTypeAndTag(Model model){
        model.addAttribute("tags",tagService.listTag());
        model.addAttribute("types",typeService.listtype());
    }

    @GetMapping("/blogs/{id}/input")
    public String editinput(@PathVariable Long id, Model model,Pageable pageable){
        setTypeAndTag(model);
        Blog blog = blogService.getBlog(id);
        blog.init();
        model.addAttribute("blog",blog);
        model.addAttribute("page",blogService.listBlog(pageable));
        all(model);
        return "admin/blogs-input";
    }

    // 发布
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes redirectAttributes){
        blog.setUser((User) session.getAttribute("user"));
        blog.setType(typeService.getType(blog.getType().getId()));
//        blog.setTags(tagService.listTag(blog.getTagIds()));
        Blog b;
        if (blog.getId() == null) {
            b =  blogService.saveBlog(blog);
        } else {
            b = blogService.updateBlog(blog.getId(), blog);
        }
        if(b==null){
            redirectAttributes.addFlashAttribute("message","新增失败");
        }
        redirectAttributes.addFlashAttribute("message","新增成功");
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String delete(@PathVariable long id,Blog blog,RedirectAttributes redirectAttributes){
        blogService.deleteBlog(id);
        redirectAttributes.addAttribute("message","删除成功");
        return "redirect:/admin/blogs";
    }

    public void  all(Model model){
        model.addAttribute("messagesize",messageService.AlistMessage().size());
        model.addAttribute("commentsize",commentService.listComment().size());
        model.addAttribute("blogVsize",blogService.countBlog());
    }
}
