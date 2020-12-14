package com.software.nju.SqlController;

import com.software.nju.Bean.SqlBean;
import com.software.nju.Service.SqlBeanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class SqlController {
    @Autowired
    SqlBeanService sqlBeanService;
    @Autowired
    SqlService sqlService;

    private static Logger logger = Logger.getLogger(SqlController.class.getName());
    //执行sql语句后之后查询到一个数字的
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/getNumber/{api}/{sqlUrl}")
    public Map<Object,Object> getNumber(@PathVariable(value = "sqlUrl") String sqlUrl, @PathVariable(value = "api") String api) throws SQLException {
        SqlBean sqlBean = sqlBeanService.getSqlBeanBySqlUrl(sqlUrl);
        Map map =null;
        if(sqlBean!=null){
            String sqlString = sqlBean.getSqlString();
            map = sqlService.getNumber(sqlString,sqlBean.getSqlUrl(),
                    sqlBean.getSqlDes(),sqlBean.getParamList(),api);
        }
        return map;
    }

    //多条sql语句的时候，数据库中的sqlString是用","分开的sql语句，parmList是用逗号分开的与sql查询结果对应的标识
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/getNumbers/{api}/{sqlUrl}")
    public Map<Object,Object> getNumbers(@PathVariable(value = "sqlUrl") String sqlUrl, @PathVariable(value = "api") String api) throws SQLException {
        SqlBean sqlBean = sqlBeanService.getSqlBeanBySqlUrl(sqlUrl);
        Map<Object,Object> res = new HashMap<>();
        if(sqlBean!=null){
            res = sqlService.getNumbers(sqlBean.getSqlString(),sqlBean.getSqlUrl(),
                    sqlBean.getSqlDes(),sqlBean.getParamList(),api);
        }
        return res;
    }

    //这个是查询多个字段的，例如sql为select * from 表名
    @CrossOrigin
    @ResponseBody
    @GetMapping(value = "/getList/{api}/{sqlUrl}")
    public Map<Object,Object> getList(@PathVariable(value = "sqlUrl") String sqlUrl, @PathVariable(value = "api") String api) throws SQLException {
        SqlBean sqlBean = sqlBeanService.getSqlBeanBySqlUrl(sqlUrl);
        Map<Object,Object> res = new HashMap<>();
        if(sqlBean!=null){
            String sqlString = sqlBean.getSqlString();
            logger.info(sqlString);
            res = sqlService.getList(sqlString,sqlBean.getSqlUrl(),sqlBean.getSqlDes(),
                    sqlBean.getParamList(),api);
        }
        return res;
    }


}
