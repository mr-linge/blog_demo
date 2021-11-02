package com.linyuegx.blog_demo.web.admin;

import com.linyuegx.blog_demo.po.Tag;
import com.linyuegx.blog_demo.po.Type;
import com.linyuegx.blog_demo.service.BlogService;
import com.linyuegx.blog_demo.service.CommentService;
import com.linyuegx.blog_demo.service.MessageService;
import com.linyuegx.blog_demo.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;


/**
 * Create by lin on  2021/10/26 12:17
 */

@Controller
@RequestMapping("/admin")
public class TagController {
    @Autowired
    TagService tagService;
    @Autowired
    BlogService blogService;
    @Autowired
    MessageService messageService;
    @Autowired
    CommentService commentService;
    @GetMapping("/tags")
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",tagService.listType(pageable));
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        return "admin/tags";
    }

    @GetMapping("/tags/input")
    public String input(Model model,Pageable pageable){
        model.addAttribute("tag",new Tag());
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        return "admin/tags-input";
    }

    @PostMapping("/tags")
    public String Post(@Valid Tag tag, BindingResult result, RedirectAttributes redirectAttributes,Model model,Pageable pageable){
        Tag typeByname = tagService.getTypeByname(tag.getName());
        if(typeByname!=null){
            result.rejectValue("name","nameError","标签名字重复");
            model.addAttribute("bpage",blogService.listBlog(pageable));
            all(model);
            return "admin/tags-input";
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag type1 = tagService.saveType(tag);
        if(type1==null){
            redirectAttributes.addFlashAttribute("message","新增失败");
        }else {
            redirectAttributes.addFlashAttribute("message","新增成功");
        }
        return  "redirect:/admin/tags";
}

    @GetMapping("/tags/{id}/input")
    public  String editinput(@PathVariable Long id, Model model,Pageable pageable){
        model.addAttribute("tag",tagService.getType(id));
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        return "admin/tags-input";
    }

    @PostMapping("/tags/{id}")
    public String edictPost(@Valid Tag tag, BindingResult result,Long id, RedirectAttributes redirectAttributes,Model model,Pageable pageable){
        Tag typeByname = tagService.getTypeByname(tag.getName());
        if(typeByname!=null){
            result.rejectValue("name","nameError","标签名字重复");
            model.addAttribute("bpage",blogService.listBlog(pageable));
            all(model);
            return "admin/tags-input";
        }
        if(result.hasErrors()){
            return "admin/tags-input";
        }
        Tag type1 = tagService.updateType(id, tag);
        if(type1 ==null){
            redirectAttributes.addFlashAttribute("message","修改失败");
        }else {
            redirectAttributes.addFlashAttribute("message","修改成功");
        }
        return  "redirect:/admin/tags";
    }

    @GetMapping("/tags/{id}/delete")
    public String delete(@PathVariable Long id ,RedirectAttributes redirectAttributes){
        try {
            tagService.deleteType(id);
        }catch (Exception e){
            redirectAttributes.addAttribute("message","删除失败");
            return  "redirect:/admin/tags";
        }

        redirectAttributes.addAttribute("message","删除成功");
        return  "redirect:/admin/tags";
    }

    public void  all(Model model){
        model.addAttribute("messagesize",messageService.AlistMessage().size());
        model.addAttribute("commentsize",commentService.listComment().size());
        model.addAttribute("blogVsize",blogService.countBlog());
    }
}
