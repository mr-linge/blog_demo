/**
 * Copyright (C), 2015-2020, LSTAR
 * FileName: PictureController
 * Author:   OneStar
 * Date:     2020/3/19 17:54
 * Description: 相册后台管理控制器
 * History:
 * <author>          <time>          <version>          <desc>
 * oneStar           修改时间           版本号              描述
 */
package com.linyuegx.blog_demo.web.admin;


import com.linyuegx.blog_demo.po.Picture;
import com.linyuegx.blog_demo.service.BlogService;
import com.linyuegx.blog_demo.service.CommentService;
import com.linyuegx.blog_demo.service.MessageService;
import com.linyuegx.blog_demo.service.PictureService;
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
 * 〈一句话功能简述〉<br> 
 * 〈相册后台管理控制器〉
 *
 * @Author: ONESTAR
 * @QQ群: 530311074
 * @URL: https://onestar.newstar.net.cn/
 * @create 2020/3/19
 * @since 1.0.0
 */
@Controller
@RequestMapping("/admin")
public class PictureController {

    @Autowired
    private PictureService pictureService;
    @Autowired
    BlogService blogService;
    @Autowired
    MessageService messageService;
    @Autowired
    CommentService commentService;

    @GetMapping("/pictures")
    public String pictures(@PageableDefault(size = 10,sort = {"id"},direction = Sort.Direction.DESC)
                                       Pageable pageable, Model model){
        model.addAttribute("page",pictureService.listPicture(pageable));
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        return "admin/pictures";
    }

    @GetMapping("/pictures/input")
    public String input(Model model,Pageable pageable) {
        model.addAttribute("picture", new Picture());
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        return "admin/pictures-input";
    }

    @GetMapping("/pictures/{id}/input")
    public String editInput(@PathVariable Long id, Model model,Pageable pageable) {
        model.addAttribute("picture", pictureService.getPicture(id));
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        return "admin/pictures-input";
    }

    @PostMapping("/pictures")
    public String post(@Valid Picture picture, BindingResult result, RedirectAttributes attributes,Model model,Pageable pageable){
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        if(result.hasErrors()){
            return "admin/pictures-input";
        }

        Picture P = pictureService.savePicture(picture);
        if (P == null ) {
            attributes.addFlashAttribute("message", "新增失败");
        } else {
            attributes.addFlashAttribute("message", "新增成功");
        }
        return "redirect:/admin/pictures";
    }

    //    编辑相册
    @PostMapping("/pictures/{id}")
    public String editPost(@Valid Picture picture, BindingResult result, @PathVariable Long id, RedirectAttributes attributes,Model model,Pageable pageable) {
        model.addAttribute("bpage",blogService.listBlog(pageable));
        all(model);
        Picture P = pictureService.updatePicture(id,picture);
        if (P == null ) {
            attributes.addFlashAttribute("message", "编辑失败");
        } else {
            attributes.addFlashAttribute("message", "编辑成功");
        }
        return "redirect:/admin/pictures";
    }

    @GetMapping("/pictures/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes attributes){
        pictureService.deletePicture(id);
        attributes.addFlashAttribute("message", "删除成功");
        return "redirect:/admin/pictures";
    }


    public void  all(Model model){
        model.addAttribute("messagesize",messageService.AlistMessage().size());
        model.addAttribute("commentsize",commentService.listComment().size());
        model.addAttribute("blogVsize",blogService.countBlog());
    }

}