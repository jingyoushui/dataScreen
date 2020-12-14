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
import com.software.nju.util.PageModel;
import com.software.nju.util.SpringbootPageable;
import com.software.nju.util.UUID;
import com.software.nju.util.urlConfig;
import com.sun.prism.shader.DrawEllipse_LinearGradient_REFLECT_AlphaTest_Loader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.criteria.CriteriaBuilder;
import java.io.*;
import java.util.ArrayList;
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

        SpringbootPageable pageable = new SpringbootPageable();
        PageModel pm=new PageModel();
        List<Sort.Order> orders = new ArrayList<Sort.Order>();  //排序
        orders.add(new Sort.Order(Sort.Direction.DESC, "id"));
        Sort sort = Sort.by(orders);

        // 开始页
        pm.setPagenumber(current);
        // 每页条数
        pm.setPagesize(size);
        pm.setSort(sort);
        pageable.setPage(pm);


        if(true){//权限认证 TODO
            Page<Visual> pagelist = visualService.findVisualByCategory(category,pageable);
            List<Visual> list = pagelist.getContent();
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
        Object visualObject = null;
        if(jsonParam.containsKey("visual")){
            visualObject = jsonParam.get("visual");
        }
        Object configObject = null;
        if(jsonParam.containsKey("config")){
            configObject = jsonParam.get("config");
        }

        if(visualObject!=null){
            visualMap = (HashMap) visualObject;
            int id = Integer.parseInt(visualMap.get("id").toString());
            Visual visual = visualService.findVisualById(id);
            if(visual!=null){
                visual.setBackgroundUrl(visualMap.get("backgroundUrl").toString());
                visualService.saveVisual(visual);
            }
        }
        if(configObject!=null){
            configMap = (HashMap) configObject;
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
        String btype = blobFile.getContentType();
        String type = btype.substring(6,btype.length());
        System.out.println(type);
        String url = name+"."+type;
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
//        fileData.setLink("http://130.39.110.118:8079/IndexImage/"+url);
//        fileData.setDomain("http://130.39.110.118:8079/");
        fileData.setLink("/IndexImage/"+url);
        fileData.setDomain("");
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

    //更改大屏的详细信息
    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/update2")
    public Response update2Visual(@RequestBody JSONObject jsonParam) {
        Response response = new Response();
        HashMap visual1 = (HashMap)jsonParam.get("visual");
        Integer id = Integer.parseInt(visual1.get("id").toString());
        System.out.println(id);
        Visual visual  = visualService.findVisualById(id);
        System.out.println(visual);
        if(visual!=null){
            visual.setPassword(visual1.get("password").toString())
                    .setCategory(Integer.parseInt(visual1.get("category").toString()))
                    .setTitle(visual1.get("title").toString())
                    .setStatus(Integer.parseInt(visual1.get("status").toString()));
            visualService.saveVisual(visual);
            response.setCode(200).setSuccess(true).setMsg("操作成功").setData(id);
        }

        return response;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/remove")
    @Transactional
    public Response remove(@RequestParam(name = "ids") Integer id) {
        Response response = new Response();
        int res = visualService.remove(id);
        if(res!=-1){
            configService.remove(id);
            response.setCode(200).setSuccess(true).setMsg("操作成功").setData(id);
        }
        return response;
    }

    @CrossOrigin
    @ResponseBody
    @RequestMapping(value = "/copy")
    @Transactional
    public Response copy(@RequestParam(name = "id") Integer id) {
        Response response = new Response();
        Visual visual = visualService.findVisualById(id);
        Visual visual1 = new Visual();
        int vid = (int) (System.currentTimeMillis() / 1000);
        //可以改成对象深拷贝，但是也麻烦
        visual1.setId(vid).setTitle(visual.getTitle()+"副本").setBackgroundUrl(visual.getBackgroundUrl())
            .setCategory(visual.getCategory()).setPassword(visual.getPassword()).setCreateUser(visual.getCreateUser())
            .setCreateDept(visual.getCreateDept()).setCreateTime(visual.getCreateTime()).setUpdateUser(visual.getUpdateUser())
            .setUpdateTime(visual.getUpdateTime()).setStatus(visual.getStatus()).setIsDeleted(visual.getIsDeleted());

        int res1 = visualService.saveVisual(visual1);
        Config config = configService.findConfigByVisual(id);
        Config config1 = new Config();

        config1.setId(vid).setVisualId(vid).setDetail(config.getDetail()).setComponent(config.getComponent());
        int res2 = configService.saveConfig(config1);
        if(res1!=-1&&res2!=-1){
            response.setCode(200).setSuccess(true).setMsg("操作成功").setData(vid);
        }
        return response;


    }

}
