package cn.edith.demo.community.controller;

import cn.edith.demo.community.dto.QuestionDTO;
import cn.edith.demo.community.mapper.QuestionMapper;
import cn.edith.demo.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionServer;

    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
      QuestionDTO questionDTO = questionServer.getById(id);
      model.addAttribute("questions",questionDTO);

      return "question";

    }
}
