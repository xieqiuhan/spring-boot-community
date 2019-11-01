package cn.edith.demo.community.controller;

import cn.edith.demo.community.dto.PaginationDTO;
import cn.edith.demo.community.dto.QuestionDTO;
import cn.edith.demo.community.mapper.QuestionMapper;
import cn.edith.demo.community.mapper.UserMapper;
import cn.edith.demo.community.model.Question;
import cn.edith.demo.community.model.User;
import cn.edith.demo.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;


@Controller
public class IndexController {
    @Autowired
    private  UserMapper userMapper;

    @Autowired
    private QuestionService questionService;
    @GetMapping("/")
    public String index(HttpServletRequest request,Model model,
                        @RequestParam(name = "page",defaultValue = "1") Integer page,
                        @RequestParam(name = "size",defaultValue = "5") Integer size) {
        Cookie[] cookies = request.getCookies();
        if(cookies != null && cookies.length != 0) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("token")) {
                    String token = cookie.getValue();
                    User user = userMapper.findByToken(token);
                    if (user != null) {
                        request.getSession().setAttribute("user", user);
                    }
                    break;
                }
            }
        }
        PaginationDTO pagination =  questionService.list(page,size);
        System.out.println(pagination);
        model.addAttribute("pagination",pagination);

        return "index";
    }

}