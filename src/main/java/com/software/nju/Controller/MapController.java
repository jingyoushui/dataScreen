package com.software.nju.Controller;

import com.alibaba.fastjson.JSONObject;
import com.software.nju.Bean.Map;
import com.software.nju.Model.MapData;
import com.software.nju.Model.Response;
import com.software.nju.Model.VisualData;
import com.software.nju.Service.MapService;
import com.software.nju.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TransferQueue;

@Controller
@RequestMapping(value = "/map")
public class MapController {
    @Autowired
    MapService mapService;

    @Autowired
    UUID uuid;

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/list")
    public Response getList(@RequestParam("current")int current, @RequestParam("size")int size){
        Response response = new Response();
        if(true){
            List<Map> res = mapService.findAll(current,size);
            for(Map m:res){
                m.setData("");
            }
            MapData mapData = new MapData();
            mapData.setCurrent(1).setTotal(20).setSize(10).setPages(10).setHitCount(false)
                    .setSearchCount(true).setRecords(res);
            response.setCode(200).setSuccess(true).setMsg("操作成功")
                    .setData(mapData);
        }
        return response;
    }

    //增加地图
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/save")
    public String save(@RequestBody JSONObject jsonParam){
        String name = jsonParam.get("name").toString();
        String data = jsonParam.get("data").toString();
        Map map = new Map();
        int id = (int) (System.currentTimeMillis() / 1000);
        map.setData(data).setName(name).setId(id+"");
        return mapService.save(map);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/data")
    public Object getMapDetail(@RequestParam(name = "id") String id){
        Map map = mapService.findMapDetail(id);
        return map.getData();

    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/detail")
    public Response getDetail(@RequestParam(name = "id") String id){
        Map map = mapService.findMapDetail(id);
        Response response = new Response();
        if(map!=null){
            response.setCode(200).setSuccess(true).setMsg("操作成功").setData(map);
        }
        return response;
    }
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/update")
    public Response updateMap(@RequestBody JSONObject jsonParam) {
       String name =  jsonParam.get("name").toString();
       String data = jsonParam.get("data").toString();
       String id = jsonParam.get("id").toString();
       Map map = mapService.findMapDetail(id);
       Response response = new Response();
       if(map!=null){
           map.setName(name).setData(data);
           mapService.save(map);
           response.setCode(200).setSuccess(true).setMsg("操作成功").setData(id);
       }
       return response;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/remove")
    @Transactional
    public Response remove(@RequestParam(name = "ids") String id) {
        Response response = new Response();
        String res = mapService.remove(id);
        if(res!=null){
            response.setCode(200).setSuccess(true).setMsg("操作成功").setData(id);
        }
        return response;
    }
}
