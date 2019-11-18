package cn.edith.demo.community.controller;

import cn.edith.demo.community.dto.CommentCreateDTO;
import cn.edith.demo.community.dto.CommentDTO;
import cn.edith.demo.community.dto.ResultDTO;
import cn.edith.demo.community.enums.CommentTypeEnum;
import cn.edith.demo.community.exception.CustomizeErrorCode;
import cn.edith.demo.community.model.Comment;
import cn.edith.demo.community.model.User;
import cn.edith.demo.community.model.UserExample;
import cn.edith.demo.community.service.CommentService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
public class CommentController {
    @Autowired
    public CommentService commentService;

    @ResponseBody
    @RequestMapping(value = "/comment" , method = RequestMethod.POST)
    public Object post(@RequestBody CommentCreateDTO commentCreateDTO,
                       HttpServletRequest request){
        User user = (User) request.getSession().getAttribute("user");
        if(user == null){
            return ResultDTO.errorOf(CustomizeErrorCode.NO_LOGIN);
        }
        if(commentCreateDTO == null || StringUtils.isBlank(commentCreateDTO.getContent())){
            return ResultDTO.errorOf(CustomizeErrorCode.CONTENT_IS_EMPTY);

        }
        Comment comment = new Comment();
        comment.setParentId(commentCreateDTO.getParentId());
        comment.setType(commentCreateDTO.getType());
        comment.setContent(commentCreateDTO.getContent());
        comment.setGmtCreate(System.currentTimeMillis());
        comment.setGmtModified(comment.getGmtCreate());
        comment.setCommentator(user.getId());
        comment.setLikeCount(0l);

        commentService.insert(comment,user);
        return ResultDTO.OkOf();
    }


    @ResponseBody
    @RequestMapping(value = "/comment/{id}" , method = RequestMethod.GET)
    public ResultDTO<List> comments(@PathVariable(name = "id") long id){
        List<CommentDTO> commentDTOs = commentService.listByTargetId(id, CommentTypeEnum.COMMENT.getType());
        return ResultDTO.OkOf(commentDTOs);
    }
}
