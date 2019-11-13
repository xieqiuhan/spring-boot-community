package cn.edith.demo.community.mapper;

import cn.edith.demo.community.model.Comment;

public interface CommentExtMapper {
    int incCommentCount(Comment comment);
}
