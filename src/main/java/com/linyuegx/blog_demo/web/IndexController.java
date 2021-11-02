package com.linyuegx.blog_demo.web;

import com.linyuegx.blog_demo.po.Blog;
import com.linyuegx.blog_demo.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.HashMap;
import java.util.Map;

@Controller
public class IndexController {
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

    @GetMapping("/")
    public String index(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("recommend_blogs", blogService.listType(10));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTag(10));
        all(model);
        return "index";
    }

    @GetMapping("/types")
    public String types(@PageableDefault(size = 10, sort = {"id"}, direction = Sort.Direction.DESC) Pageable pageable, Model model) {
        model.addAttribute("page", blogService.listBlog(pageable));
        model.addAttribute("recommend_blogs", blogService.listType(10));
        model.addAttribute("types", typeService.listTypeTop(6));
        model.addAttribute("tags", tagService.listTag(10));
        return "types";
    }
    @GetMapping("/blog/{id}")
    public String blog(@PathVariable Long id,Model model){
        Blog andConvert = blogService.getAndConvert(id);
        andConvert.setViews(andConvert.getViews()+1);
        blogService.saveBlog(andConvert);
        model.addAttribute("blog",andConvert);
        return "blog";
    }
    @PostMapping("/search")
    public String search(@PageableDefault(size = 10, sort = {"updateTime"}, direction = Sort.Direction.DESC) Pageable pageable, @RequestParam String query, Model model) {
        model.addAttribute("page",blogService.listBlog(pageable,"%"+query+"%"));
        model.addAttribute("query",query);
        return "search";

}
    public void  all(Model model){
        model.addAttribute("messagesize",messageService.AlistMessage().size());
        model.addAttribute("commentsize",commentService.listComment().size());
        model.addAttribute("blogVsize",blogService.countBlog());
    }
}