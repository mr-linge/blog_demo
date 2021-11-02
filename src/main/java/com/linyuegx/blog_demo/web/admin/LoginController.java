package com.linyuegx.blog_demo.web.admin;

import com.linyuegx.blog_demo.po.Message;
import com.linyuegx.blog_demo.po.User;
import com.linyuegx.blog_demo.service.BlogService;
import com.linyuegx.blog_demo.service.CommentService;
import com.linyuegx.blog_demo.service.MessageService;
import com.linyuegx.blog_demo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * Create by lin on  2021/10/23 18:54
 */
@Controller
@RequestMapping("/admin")
public class LoginController {
    @Autowired
    private UserService userService;
    @Autowired
    BlogService blogService;
    @Autowired
    MessageService messageService;
    @Autowired
    CommentService commentService;
    @GetMapping()
    public String loginpage(){
        return "admin/login";
    }
    @PostMapping("/login")
    public String login(@RequestParam String username,
                        @RequestParam String password,
                        HttpSession session,
                        RedirectAttributes attributes, Model model, Pageable pageable){
        User user = userService.checkUser(username, password);
        if (user!=null){
            user.setPassword(null);
            session.setAttribute("user",user);
            model.addAttribute("page",blogService.listBlog(pageable));
            all(model);
            return "admin/index";
        }
        attributes.addFlashAttribute("message","用户名和密码错误");
        return "redirect:/admin";
    }

    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/admin";
    }

    public void  all(Model model){
        model.addAttribute("messagesize",messageService.AlistMessage().size());
        model.addAttribute("commentsize",commentService.listComment().size());
        model.addAttribute("blogVsize",blogService.countBlog());
    }
}
