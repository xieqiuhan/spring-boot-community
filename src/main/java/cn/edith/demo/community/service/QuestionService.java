package cn.edith.demo.community.service;

import cn.edith.demo.community.dto.PaginationDTO;
import cn.edith.demo.community.dto.QuestionDTO;
import cn.edith.demo.community.mapper.QuestionMapper;
import cn.edith.demo.community.mapper.UserMapper;
import cn.edith.demo.community.model.Question;
import cn.edith.demo.community.model.User;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {
    @Autowired
    private UserMapper userMapper;

    @Autowired
    private QuestionMapper questionMapper;
    public PaginationDTO list(Integer page, Integer size) {
        Integer offset = size*(page-1);
        List<Question> questions = questionMapper.list(offset,size);
        PaginationDTO paginationDTO = new PaginationDTO();
        Integer totalCount = questionMapper.count();
        paginationDTO.setPagination(totalCount,page,size);

        if(page <1){
            page =1;
        }
        if(page > paginationDTO.getTotalPage()){
            page = paginationDTO.getTotalPage();
        }

        List<QuestionDTO> questionDTOLists = new ArrayList<>();

        for(Question question:questions){

           User user = userMapper.findById(question.getCreator());

           QuestionDTO questionDTO = new QuestionDTO();
           BeanUtils.copyProperties(question,questionDTO);
           questionDTO.setUser(user);
           questionDTOLists.add(questionDTO);
        }
        paginationDTO.setQuestions(questionDTOLists);



        return paginationDTO;
    }

}
