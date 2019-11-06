package cn.edith.demo.community.mapper;


import cn.edith.demo.community.dto.QuestionDTO;
import cn.edith.demo.community.model.Question;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;


import java.util.List;

@Mapper
@Repository
public interface QuestionMapper {

    @Insert("insert into question (title,description,gmt_create,gmt_modified,creator,tag) values (#{title},#{description},#{gmtCreate},#{gmtModified},#{creator},#{tag})")
    void create(Question question);

    @Select("select * from question limit #{offset}, #{size}")
    List<Question> list(@Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question")
    Integer count();

    @Select("select * from question  where creator = #{id}  limit #{offset}, #{size}")
    List<Question> listByuserId(@Param(value = "id") Long id, @Param(value = "offset") Integer offset, @Param(value = "size") Integer size);

    @Select("select count(1) from question where creator = #{id}")
    Integer countByUserId(@Param(value = "id") Long id);

    @Select("select * from question  where id = #{id} " )
    Question getById(@Param(value = "id") Long id);


    @Update("update question set title = #{title},description = #{description} , gmt_modified = #{gmtModified},tag = #{tag} where id = #{id}" )
    void update(Question question);
}
