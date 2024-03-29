 package cn.edith.demo.community.advice;

import cn.edith.demo.community.dto.ResultDTO;
import cn.edith.demo.community.exception.CustomizeErrorCode;
import cn.edith.demo.community.exception.CustomizeException;
import com.alibaba.fastjson.JSON;
import javafx.print.Printer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.ModelAndViewDefiningException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

 @ControllerAdvice
public class CustomizeExceptionHandler {
    @ExceptionHandler(Exception.class)
   ModelAndView handle(Throwable e, Model model, HttpServletRequest request, HttpServletResponse response) {
        String contentType  = request.getContentType();
        if ("application/json".equals(contentType)) {
            ResultDTO resultDTO ;
            //返回json
            if(e instanceof CustomizeException){
                resultDTO = ResultDTO.errorOf((CustomizeException) e);
            }else{
                resultDTO = ResultDTO.errorOf(CustomizeErrorCode.SYS_ERROR);
            }
            try{
                response.setContentType("application/json");
                response.setStatus(200);
                response.setCharacterEncoding("UTF-8");
                PrintWriter writer = response.getWriter();
                writer.write(JSON.toJSONString(resultDTO));
                writer.close();
            }catch (IOException ioe){

            }
            return null;
        }else{
            //错误页面跳转
            if(e instanceof CustomizeException){
                model.addAttribute("message",e.getMessage());
            }else{
                model.addAttribute("message","服务冒烟了");
            }
            return new ModelAndView("error");
        }

    }
}

