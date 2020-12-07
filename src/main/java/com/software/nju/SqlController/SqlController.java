package com.software.nju.SqlController;

import com.fasterxml.jackson.databind.annotation.JsonAppend;
import com.software.nju.Bean.SqlBean;
import com.software.nju.Service.SqlBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;

@Controller
//静海数据库
@RequestMapping(value = "/api")
public class SqlController {
    @Autowired
    SqlBeanService sqlBeanService;
    @Autowired
    SQLToBean sqlToBean;


    @CrossOrigin
    @ResponseBody
    @PostMapping(value = "/post")
    public Object postValue(){

        return null;
    }

    //执行sql语句后之后查询到一个数字的
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/getNumber/{sqlUrl}")
    public Map<String,Integer> getNumber(@PathVariable(value = "sqlUrl")String sqlUrl) throws SQLException {
        SqlBean sqlBean = sqlBeanService.getSqlBeanBySqlUrl(sqlUrl);
        Map map = new HashMap();
        if(sqlBean!=null){
            String sqlString = sqlBean.getSqlString();
            String[] param;
            if(sqlBean.getParamList()!=null){
                param = sqlBean.getParamList().split(",");
            }
            Date t = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(t).substring(0,8)+"01 00:00:00.000";
            //在写sql语句的时候，如果涉及到日期，如查询到目前为止的数据总数，日期部分用#代替，然后在这里替换成当前的时间
            sqlString = sqlString.replaceFirst("#",date);
            int count  = sqlToBean.getNumber(sqlString);
            map.put("value",count);

        }
        return map;
    }

    //多条sql语句的时候，数据库中的sqlString是用","分开的sql语句，parmList是用逗号分开的与sql查询结果对应的标识
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/getNumbers/{sqlUrl}")
    public List<HashMap<String,Object>> getNumbers(@PathVariable(value = "sqlUrl")String sqlUrl) throws SQLException {
        SqlBean sqlBean = sqlBeanService.getSqlBeanBySqlUrl(sqlUrl);
        List<HashMap<String,Object>> list = new ArrayList<>();
        if(sqlBean!=null){
            List<String> sqlString = new ArrayList<>();
            if(sqlBean.getSqlString()!=null){
                sqlString = Arrays.asList(sqlBean.getSqlString().split(","));
            }

            List<String> param = new ArrayList<>();
            if(sqlBean.getParamList()!=null){
                param = Arrays.asList(sqlBean.getParamList().split(","));
            }
            Date t = new Date();
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            String date = df.format(t).substring(0,8)+"01 00:00:00.000";
            String sql = "";

            for(int i=0;i<sqlString.size();i++){
                sql = sqlString.get(i).replaceFirst("#",date);
                System.out.println(sql);
                int a = sqlToBean.getNumber(sql);
                HashMap map = new HashMap();
                map.put("name",param.get(i));
                map.put("value",a);
                list.add(map);
            }
        }
        return list;
    }

    //这个是查询多个字段的，例如sql为select * from 表名
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/getList/{sqlUrl}")
    public List<HashMap<String,String>> getList(@PathVariable(value = "sqlUrl")String sqlUrl) throws SQLException {
        SqlBean sqlBean = sqlBeanService.getSqlBeanBySqlUrl(sqlUrl);
        List<HashMap<String,String>> list = new ArrayList<>();
        if(sqlBean!=null){
            String sqlString = sqlBean.getSqlString();
            String[] param;
            if(sqlBean.getParamList()!=null){
                param = sqlBean.getParamList().split(",");
            }
            list = sqlToBean.getList(sqlString);

        }

        return list;
    }


}
