package com.software.nju.Controller;

import ch.qos.logback.core.pattern.util.RegularEscapeUtil;
import com.alibaba.fastjson.JSONObject;
import com.software.nju.Bean.Category;
import com.software.nju.Model.Response;
import com.software.nju.Service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

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

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/save")
    public Response save(@RequestBody JSONObject jsonParam){
        String categoryKey = jsonParam.get("categoryKey").toString();
        String categoryValue = jsonParam.get("categoryValue").toString();
        Category category = new Category();
        String id = (int) (System.currentTimeMillis() / 1000)+"";
        category.setId(id).setCategoryKey(categoryKey).setCategoryValue(categoryValue).setIsDeleted(0);
        String res = categoryService.save(category);
        Response response = new Response();

        if(res!=""){
            response.setCode(200).setMsg("操作成功").setSuccess(true);
        }
        return response;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/detail")
    public Response Detail(@RequestParam(name = "id") String id){
        Category category = categoryService.getCategory(id);
        Response response = new Response();
        if(category!=null){
            response.setCode(200).setSuccess(true).setMsg("操作成功").setData(category);
        }
        return response;
    }
}
