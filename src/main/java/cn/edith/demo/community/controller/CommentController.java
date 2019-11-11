package cn.edith.demo.community.controller;

import cn.edith.demo.community.dto.CommentDTO;
import cn.edith.demo.community.dto.ResultDTO;
import cn.edith.demo.community.exception.CustomizeErrorCode;
import cn.edith.demo.community.exception.CustomizeException;
import cn.edith.demo.community.mapper.CommentMapper;
import cn.edith.demo.community.model.Comment;
import cn.edith.demo.community.model.User;
import cn.edith.demo.community.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.xml.transform.Result;
import java.util.HashMap;
import java.util.Map;

@Controller
public class CommentController {
    @Autowired
    public CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment" , method = RequestMethod.POST)
    public Object post(@RequestBody CommentDTO commentDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        Comment comment = new Comment();
        comment.setParentId(commentDTO.getParentId());
        comment.setType(commentDTO.getType());
        comment.setContent(commentDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0l);
        commentService.insert(comment);
        return ResultDTO.OkOf();
    }
}
