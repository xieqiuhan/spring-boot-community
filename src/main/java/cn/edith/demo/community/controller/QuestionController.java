package cn.edith.demo.community.controller;

import cn.edith.demo.community.dto.CommentDTO;
import cn.edith.demo.community.dto.QuestionDTO;
import cn.edith.demo.community.enums.CommentTypeEnum;
import cn.edith.demo.community.service.CommentService;
import cn.edith.demo.community.service.QuestionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
public class QuestionController {
    @Autowired
    private QuestionService questionServer;
    @Autowired
    private CommentService commentService;

    @GetMapping("question/{id}")
    public String question(@PathVariable(name = "id") Long id,
                           Model model){
      QuestionDTO questionDTO = questionServer.getById(id);
      List<CommentDTO> commentDTOs = commentService.listByTargetId(id, CommentTypeEnum.QUESTION.getType());
      model.addAttribute("comments",commentDTOs);

      //增加累计阅读数
      questionServer.incView(id);
      model.addAttribute("questions",questionDTO);
      return "question";

    }
}
