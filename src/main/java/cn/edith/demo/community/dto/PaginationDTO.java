package cn.edith.demo.community.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;
@Data
public class PaginationDTO {
    private List<QuestionDTO> questions;
    private Boolean showPrevious;
    private Boolean showFirstPage;
    private Boolean showNext;
    private Boolean showEndPage;
    private Integer page;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;
    public void setPagination(Integer totalCount, Integer page, Integer size) {
      if(totalCount%size == 0){
          totalPage = totalCount/size;
      }else{
          totalPage = totalCount/size +1;
      }
        this.page = page;

      pages.add(page);
   //   System.out.println(pages);
      for(int i=1;i<3;i++){
          if(page - i >0){
              pages.add(0,page-i);
          }
          if(page +i <= totalPage){
                pages.add(page+i);
          }
      }
      //是否展示上一页按钮
      if(page == 1){
          showPrevious = false;
      }else{
          showPrevious = true;
      }
      //是否展示下一页按钮
      if(page == totalPage) {
          showNext = false;
      }else{
          showNext = true;
      }
      //是否显示第一页
        if(pages.contains(1)){
            showFirstPage = false;
        }else{
            showFirstPage = true;
        }

        //是否显示最后一页
        if(pages.contains(totalPage)){
            showEndPage = false;
        }else{
            showEndPage = true;
        }
      }

    }
