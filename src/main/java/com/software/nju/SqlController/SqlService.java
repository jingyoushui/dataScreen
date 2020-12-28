package com.software.nju.SqlController;

import com.software.nju.Enum.ApiEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.logging.Logger;

@Service
public class SqlService {
    @Autowired
    SQLToBean sqlToBean;

    private static Logger logger = Logger.getLogger(SqlService.class.getName());

    public Map<String,Object> getNumber(String sqlString,String url,String des, String params, String api) throws SQLException {
        Map map = new HashMap();

        Date t = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(t).substring(0,10)+" 00:00:00.000";
        //在写sql语句的时候，如果涉及到日期，如查询到目前为止的数据总数，日期部分用#代替，然后在这里替换成当前的时间
        sqlString = sqlString.replaceFirst("#",date);
//        logger.info(sqlString);
//        List<String> param = new ArrayList<>();
//        if(params!=null){
//            param = Arrays.asList(params.split(","));
//        }
        int count = 0;
        if(api.equals(ApiEnum.getValueByApi("JHApi"))){
            count = sqlToBean.getJHNumber(sqlString);
        }else if(api.equals(ApiEnum.getValueByApi("XFApi"))){
            count = sqlToBean.getGYNumber(sqlString);
        }

        map.put("url",url);
        map.put("description",des);
        map.put("value",count);
        return map;

    }

    public Map<Object,Object> getNumbers(String sqls,String url,String des, String params, String api) throws SQLException {
        List<Map<String,Object>> list = new ArrayList<>();
        Map<Object,Object> res = new HashMap<>();
        Date t = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        String date = df.format(t).substring(0,10)+" 00:00:00.000";
        String sql = "";
        List<String> sqlString = new ArrayList<>();
        if(sqls!=null){
            sqlString = Arrays.asList(sqls.split(";"));
        }

        List<String> param = new ArrayList<>();
        if(params!=null){
            param = Arrays.asList(params.split(","));
        }

        for(int i=0;i<sqlString.size();i++){
            sql = sqlString.get(i).replaceFirst("#",date);
//            logger.info(sql);
            int a = 0;
            if(api.equals(ApiEnum.getValueByApi("JHApi"))){
                a = sqlToBean.getJHNumber(sql);
            }else if(api.equals(ApiEnum.getValueByApi("XFApi"))){
                a = sqlToBean.getGYNumber(sql);
            }
            HashMap map = new HashMap();
            map.put("name",param.get(i));
            map.put("value",a);
            list.add(map);
        }
        res.put("url",url);
        res.put("description",des);
        res.put("value",list);
        return res;
    }

    public Map<Object,Object> getList(String sqls,String url,String des, String params, String api) throws SQLException {
        List<Map<String,String>> list = new ArrayList<>();
        Map<Object,Object> res = new HashMap<>();
        List<String> param = new ArrayList<>();
        if(params!=null){
            param = Arrays.asList(params.split(","));
        }
        if(api.equals(ApiEnum.getValueByApi("JHApi"))){
            list = sqlToBean.getJHList(sqls,param);
        }else if(api.equals(ApiEnum.getValueByApi("XFApi"))) {
            list = sqlToBean.getGYList(sqls,param);
        }
        res.put("url",url);
        res.put("description",des);
        res.put("value",list);
        return res;
    }

}
