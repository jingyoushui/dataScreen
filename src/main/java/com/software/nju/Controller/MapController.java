package com.software.nju.Controller;

import com.software.nju.Bean.Map;
import com.software.nju.Model.MapData;
import com.software.nju.Model.Response;
import com.software.nju.Model.VisualData;
import com.software.nju.Service.MapService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/map")
public class MapController {
    @Autowired
    MapService mapService;

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
            System.out.println(res);
            MapData mapData = new MapData();
            mapData.setCurrent(1).setTotal(20).setSize(10).setPages(10).setHitCount(false)
                    .setSearchCount(true).setRecords(res);
            response.setCode(200).setSuccess(true).setMsg("操作成功")
                    .setData(mapData);
        }
        return response;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/save")
    public String save(@RequestBody Map map ){
        return mapService.save(map);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/data")
    public Object getMapDetail(@RequestParam(name = "id") String id){
        Map map = mapService.findMapDetail(id);
        return map.getData();

    }
}
