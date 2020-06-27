package com.software.nju.Controller;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSONObject;
import com.software.nju.Bean.Config;
import com.software.nju.Bean.Visual;
import com.software.nju.Model.FileData;
import com.software.nju.Model.Response;
import com.software.nju.Model.VisualData;
import com.software.nju.Model.VisualDetail;
import com.software.nju.Service.ConfigService;
import com.software.nju.Service.VisualService;
import com.software.nju.util.UUID;
import com.software.nju.util.urlConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping(value = "/visual")
public class VisualController {

    @Autowired
    VisualService visualService;

    @Autowired
    private UUID uuid;

    @Autowired
    ConfigService configService;

    private String fileurl = urlConfig.devfile;


    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/list")
    public Response getVisual(@RequestParam("category")int category,
                              @RequestParam("current")int current,@RequestParam("size")int size){
        Response response = new Response();

        if(true){//权限认证
            List<Visual> list = visualService.findVisualByCategory(category);
            VisualData visualData = new VisualData();
            visualData.setCurrent(1).setTotal(20).setSize(10).setPages(10).setHitCount(false)
                    .setSearchCount(true).setRecords(list);
            response.setCode(200).setSuccess(true).setMsg("操作成功")
                    .setData(visualData);
        }
        return response;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/detail")
    public Response getDetail(@RequestParam(name = "id") Integer id){

        Response response = new Response();
        if(true){//权限认证 TODO
            Visual visual = visualService.findVisualById(id);
            if(visual!=null){
                Config config = configService.findConfigByVisual(id);
                VisualDetail visualDetail = new VisualDetail(visual,config);
                response.setCode(200).setSuccess(true).setMsg("操作成功")
                        .setData(visualDetail);
            }else{

            }


        }
        return response;


    }

    //保存大屏的详细信息
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/saveDetail")
    public int saveVisualDetail(@RequestBody Config config) {
        return configService.saveConfig(config);
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/update")
    public Response update(@RequestBody JSONObject jsonParam){
        Response response = new Response();
        HashMap visualMap,configMap;
        Object visualObject = jsonParam.get("visual");
        Object configObject = jsonParam.get("config");
        if(visualObject!=null){
            visualMap = (HashMap) visualObject;
            System.out.println(visualMap);
            int id = Integer.parseInt(visualMap.get("id").toString());
            Visual visual = visualService.findVisualById(id);
            if(visual!=null){
                visual.setBackgroundUrl(visualMap.get("backgroundUrl").toString());
                visualService.saveVisual(visual);
            }
        }
        if(configObject!=null){
            configMap = (HashMap) configObject;
            System.out.println(configMap);
            int id = Integer.parseInt(configMap.get("id").toString());
            int visualId = Integer.parseInt(configMap.get("visualId").toString());
            String detail = configMap.get("detail").toString();
            String component = configMap.get("component").toString();
            Config config = configService.findConfigByVisual(visualId);
            if(config!=null){
                config.setDetail(detail).setComponent(component);
                configService.saveConfig(config);
            }

        }

        response.setCode(200).setSuccess(true).setMsg("操作成功");

        return response;
    }

    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/put-file")
    public Response save(@RequestParam("file") MultipartFile blobFile) {



        String name = uuid.getUUID();
        String url = name+".jpg";
        File f = null;
        f = new File(fileurl+url);
        try (InputStream in  = blobFile.getInputStream(); OutputStream os = new FileOutputStream(f)){
            // 得到文件流。以文件流的方式输出到新文件
            // 可以使用byte[] ss = multipartFile.getBytes();代替while
            byte[] buffer = new byte[4096];
            int n;
            while ((n = in.read(buffer,0,4096)) != -1){
                os.write(buffer,0,n);
            }
        }catch (IOException e){
            e.printStackTrace();
        }
        // 输出路径
        System.out.println(f.getAbsolutePath());

        Response response = new Response();
        FileData fileData = new FileData();
        fileData.setLink("http://localhost/IndexImage/"+url);
        fileData.setDomain("http://localhost/");
        fileData.setName(url);
        fileData.setOriginalName(url);
        response.setCode(200).setSuccess(true).setMsg("操作成功").setData(fileData);
        return  response;
    }

    //保存大屏的详细信息
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/save")
    public Response saveVisual(@RequestBody JSONObject jsonParam) {
        Response response = new Response();
        HashMap visual1 = (HashMap)jsonParam.get("visual");
        HashMap config1 = (HashMap)jsonParam.get("config");
        Visual visual = new Visual();
        int id = (int) (System.currentTimeMillis() / 1000);
        System.out.println(id);
        visual.setId(id).setIsDeleted(0).setStatus(1);
        visual.setPassword(visual1.get("password").toString())
                .setCategory(Integer.parseInt(visual1.get("category").toString()))
                .setTitle(visual1.get("title").toString());

        Config config = new Config();
        config.setVisualId(id).setId(id).setDetail(config1.get("detail").toString())
                .setComponent(config1.get("component").toString());

        if(visualService.saveVisual(visual)!=-1 && configService.saveConfig(config)!=-1){
            Map<String, Integer> res = new HashMap<>();
            res.put("id",id);
            response.setCode(200).setSuccess(true).setMsg("操作成功").setData(res);
        }


        return  response;
    }

}
