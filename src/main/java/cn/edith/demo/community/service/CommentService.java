package cn.edith.demo.community.service;

import cn.edith.demo.community.enums.CommentTypeEnum;
import cn.edith.demo.community.exception.CustomizeErrorCode;
import cn.edith.demo.community.exception.CustomizeException;
import cn.edith.demo.community.mapper.CommentMapper;
import cn.edith.demo.community.mapper.QuestionExtMapper;
import cn.edith.demo.community.mapper.QuestionMapper;
import cn.edith.demo.community.model.Comment;
import cn.edith.demo.community.model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CommentService {
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private QuestionMapper questionMapper;
    @Autowired
    private QuestionExtMapper questionExtMapper;

    @Transactional
    public void insert(Comment comment) {
        if(comment.getParentId() == null || comment.getParentId() == 0){
            throw new CustomizeException(CustomizeErrorCode.TARGET_PARAM_NOT_FOUND);
        }
        if(comment.getType() == null || !CommentTypeEnum.isExist(comment.getType())){
            throw new CustomizeException(CustomizeErrorCode.TYPE_PARAM_WRONG);
        }
        if(comment.getType() == CommentTypeEnum.COMMENT.getType()){
            //回复评论
           Comment dbComment = commentMapper.selectByPrimaryKey(comment.getParentId());
           if(dbComment == null){
               throw new CustomizeException((CustomizeErrorCode.COMMENT_NOT_FOUND));
           }
           commentMapper.insertSelective(comment);
        }else{
            //回复问题
            Question dbQuestion = questionMapper.selectByPrimaryKey(comment.getParentId());
            if(dbQuestion == null){
                throw new CustomizeException((CustomizeErrorCode.QUESTION_NOT_FOUND));
            }else{
                dbQuestion.setCommentCount(1);
                commentMapper.insertSelective(comment);
                questionExtMapper.incCommentCount(dbQuestion);
            }

        }

    }
}
