package com.software.nju.Controller;

import com.software.nju.Bean.Category;
import com.software.nju.Model.Response;
import com.software.nju.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping(value = "/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/list")
    public Response getAll(){
        Response response = new Response();

        if(true){//这里需要权限认证
            response.setCode(200).setSuccess(true).setData(categoryService.findAll()).setMsg("操作成功");
        }
        return response;
    }
}
