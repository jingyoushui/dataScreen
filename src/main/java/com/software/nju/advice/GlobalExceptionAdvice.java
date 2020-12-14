package com.software.nju.advice;

import com.software.nju.Model.Response;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import javax.servlet.http.HttpServletRequest;


@RestControllerAdvice
public class GlobalExceptionAdvice {
    @ExceptionHandler(Exception.class)
    public Object defaultErrorHandler(HttpServletRequest request, Exception e){
        Response response = new Response();
        response.setCode(HttpStatus.INTERNAL_SERVER_ERROR.value()).setSuccess(false);
        response.setMsg(e.getMessage());
        response.setData(request.getRequestURL().toString());
        return response;
    }

}
