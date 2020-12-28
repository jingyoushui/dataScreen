package com.software.nju.Controller;


import com.alibaba.fastjson.JSONObject;
import com.software.nju.Bean.Category;
import com.software.nju.Bean.Map;
import com.software.nju.Bean.Visual;
import com.software.nju.Model.PageData;
import com.software.nju.Model.Response;
import com.software.nju.Service.SqlBeanService;
import com.software.nju.Vo.SqlBeanVo;
import com.software.nju.util.PageModel;
import com.software.nju.util.SpringbootPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Controller
@RequestMapping(value = "/data")
public class SqlBeanController {

    @Autowired
    SqlBeanService sqlBeanService;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/list")
    public Response getAll(@RequestParam("current")int current,@RequestParam("size")int size){

        Response response = new Response();

        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pm=new PageModel();
        List<Sort.Order> orders = new ArrayList<Sort.Order>();  //排序
        orders.add(new Sort.Order(Sort.Direction.ASC, "id"));
        Sort sort = Sort.by(orders);

        // 开始页
        pm.setPagenumber(current);
        // 每页条数
        pm.setPagesize(size);
        pm.setSort(sort);
        pageable.setPage(pm);

        List<SqlBeanVo> list = sqlBeanService.getAll(pageable);
        PageData pageData = new PageData();
        int total = sqlBeanService.getCount();
        pageData.setCurrent(current).setTotal(total).setSize(size).setPages((int) Math.ceil((double)total/size)).setHitCount(false)
                .setSearchCount(true).setRecords(list);
        response.setCode(200).setSuccess(true).setMsg("操作成功")
                .setData(pageData);

        return response;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/detail")
    public Response getSqlBeanVosByWebsocketId(@RequestParam(name = "id") String id){
        List<SqlBeanVo> list = sqlBeanService.findSqlBeanVosByWebsocketId(id);
        Response response = new Response();
        if(list!=null){
            response.setCode(200).setSuccess(true).setMsg("操作成功").setData(list);
        }
        return response;
    }


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/update")
    public Response updateMap(@RequestBody SqlBeanVo sqlBeanVo) {
        Response response = new Response();
        if(sqlBeanVo!=null){
            int id = sqlBeanService.save(sqlBeanVo);
            response.setCode(200).setSuccess(true).setMsg("操作成功").setData(id);
        }
        return response;
    }
}
