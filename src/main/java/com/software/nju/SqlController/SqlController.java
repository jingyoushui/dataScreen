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
import java.util.*;

@Controller
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
            int count  = sqlToBean.getNumber(sqlString);
            map.put("value",count);

        }
        return map;
    }

    //多条sql语句的时候，数据库中的sqlString是用","分开的sql语句，parmList是用逗号分开的与sql查询结果对应的标识
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/getNumbers/{sqlUrl}")
    public Map<String,Integer> getNumbers(@PathVariable(value = "sqlUrl")String sqlUrl) throws SQLException {
        SqlBean sqlBean = sqlBeanService.getSqlBeanBySqlUrl(sqlUrl);
        Map map = new HashMap();
        if(sqlBean!=null){
            List<String> sqlString = new ArrayList<>();
            if(sqlBean.getSqlString()!=null){
                sqlString = Arrays.asList(sqlBean.getSqlString().split(","));
            }

            List<String> param = new ArrayList<>();
            if(sqlBean.getParamList()!=null){
                param = Arrays.asList(sqlBean.getParamList().split(","));
            }
            for(int i=0;i<sqlString.size();i++){
                int a = sqlToBean.getNumber(sqlString.get(i));
                map.put(param.get(i),a);
            }
        }
        return map;
    }

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
