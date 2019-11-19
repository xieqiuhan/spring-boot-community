package cn.edith.demo.community.controller;

import cn.edith.demo.community.dto.FileDTO;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;

@Controller
public class FileController {
    @RequestMapping("/file/upload")
    @ResponseBody
    public FileDTO upload(HttpServletResponse response){
        FileDTO fileDTO = new FileDTO();
        fileDTO.setMessage("1");
        fileDTO.setSuccess(1);
        fileDTO.setUrl("/static/images/lala.jpg");
        return fileDTO;


    }
}
