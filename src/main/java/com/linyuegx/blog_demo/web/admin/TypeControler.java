package com.linyuegx.blog_demo.web.admin;

import com.linyuegx.blog_demo.NotFoundException;
import com.linyuegx.blog_demo.po.Type;
import com.linyuegx.blog_demo.service.BlogService;
import com.linyuegx.blog_demo.service.CommentService;
import com.linyuegx.blog_demo.service.MessageService;
import com.linyuegx.blog_demo.service.TypeService;
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
 * Create by lin on  2021/10/26 1:05
 */

@Controller
@RequestMapping("/admin")
public class TypeControler {

    @Autowired
    private TypeService typeService;
    @Autowired
    MessageService messageService;
    @Autowired
    CommentService commentService;
    @Autowired
    BlogService blogService;

    @PostMapping("/types")
    public String Post(@Valid Type type, BindingResult result, RedirectAttributes redirectAttributes,Model model,Pageable pageable){
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        Type typeByname = typeService.getTypeByname(type.getName());
        if(typeByname!=null){
            result.rejectValue("name","nameError","类型名字重复");
            model.addAttribute("page",typeService.listType(pageable));
            all(model);
            return "admin/types-input";
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = typeService.saveType(type);
        if(type1==null){
            redirectAttributes.addFlashAttribute("message","新增失败");
        }else {
            redirectAttributes.addFlashAttribute("message","新增成功");
        }
        return  "redirect:/admin/types";
    }

    @GetMapping("/types")
    public String list(@PageableDefault(size = 5,sort = {"id"},direction = Sort.Direction.DESC) Pageable pageable, Model model){
        model.addAttribute("page",typeService.listType(pageable));
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        return "admin/types";
    }

    @GetMapping("/types/input")
    public String input(Model model,Pageable pageable){
        model.addAttribute("type",new Type());
        model.addAttribute("page",typeService.listType(pageable));
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        return "admin/types-input";
    }
    @GetMapping("/types/{id}/input")
    public  String editinput(@PathVariable Long id, Model model,Pageable pageable){
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        model.addAttribute("type",typeService.getType(id));
        model.addAttribute("page",typeService.listType(pageable));
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        return "admin/types-input";
    }




    @PostMapping("/types/{id}")
    public String edictPost(@Valid Type type, BindingResult result,Long id, RedirectAttributes redirectAttributes,Model model,Pageable pageable){
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        Type typeByname = typeService.getTypeByname(type.getName());
        if(typeByname!=null){
            result.rejectValue("name","nameError","类型名字重复");
            model.addAttribute("page",typeService.listType(pageable));
            all(model);
            return "admin/types-input";
        }
        if(result.hasErrors()){
            return "admin/types-input";
        }
        Type type1 = typeService.updateType(id, type);
        if(type1 ==null){
            redirectAttributes.addFlashAttribute("message","修改失败");
        }else {
            redirectAttributes.addFlashAttribute("message","修改成功");
        }
        return  "redirect:/admin/types";
    }

    @GetMapping("/types/{id}/delete")
    public String delete(@PathVariable Long id ,RedirectAttributes redirectAttributes){
        try {
            typeService.deleteType(id);
        }catch (Exception e){
            redirectAttributes.addAttribute("message","删除失败");
            return  "redirect:/admin/types";
        }
        redirectAttributes.addAttribute("message","删除成功");
        return  "redirect:/admin/types";
    }

    public void  all(Model model){
        model.addAttribute("messagesize",messageService.AlistMessage().size());
        model.addAttribute("commentsize",commentService.listComment().size());
        model.addAttribute("blogVsize",blogService.countBlog());
    }
}
