package cn.edith.demo.community.mapper;

import cn.edith.demo.community.model.Question;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

public interface QuestionExtMapper {



    int incView(Question record);
    int incCommentCount(Question record);
//
//    List<Question> selectRelated(Question question);
//
//    Integer countBySearch(QuestionQueryDTO questionQueryDTO);
//
//    List<Question> selectBySearch(QuestionQueryDTO questionQueryDTO);
}
