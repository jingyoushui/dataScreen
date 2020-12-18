package com.software.nju.SqlController;

import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SQLToBean {

    /**
     * 获取高院的数据库连接，如果是静海区的数据库，使用默认的连接，调用方法时候不需要传递connection
     * 调用高院的数据库时，是需要调用带有connection形参的方法，将该connection传入
     */
    Connection GYconnection = SQLHelper.getGYConnection();
    Connection JHconnection = SQLHelper.getJHConnection();

    public Integer getJHNumber(String sql) throws SQLException {
        ResultSet res = SQLHelper.getJHResultSet(JHconnection,sql);
        Map<String, Integer> map = new HashMap<>();
        int number = 0;
        if (res.next()) {
            number = res.getInt(1);
        }
        return number;
    }

    public List<Map<String, String>> getJHList(String sql,List<String> parms) throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();
        ResultSet rs = SQLHelper.getJHResultSet(JHconnection,sql);
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount(); //Map rowData;

        while (rs.next()) { //rowData = new HashMap(columnCount);
            HashMap rowData = new HashMap<String, String>();
            for (int i = 1; i <= columnCount; i++) {
                if(parms.size()==0){
                    rowData.put(md.getColumnName(i),rs.getObject(i)+"");
                }else{
                    rowData.put(parms.get(i-1), rs.getObject(i)+"");
                }
            }
            list.add(rowData);
        }
        return list;
    }

    public Integer getGYNumber(String sql) throws SQLException {
        ResultSet res = SQLHelper.getGYResultSet(GYconnection,sql);
        Map<String, Integer> map = new HashMap<>();
        int number = 0;
        if (res.next()) {
            number = res.getInt(1);
        }
        return number;
    }

    public List<Map<String, String>> getGYList(String sql,List<String> parms) throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();
        ResultSet rs = SQLHelper.getGYResultSet(GYconnection,sql);
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount(); //Map rowData;

        while (rs.next()) { //rowData = new HashMap(columnCount);
            HashMap rowData = new HashMap<String, String>();
            for (int i = 1; i <= columnCount; i++) {
                if(parms.size()==0){
                    rowData.put(md.getColumnName(i),rs.getObject(i)+"");
                }else{
                    rowData.put(parms.get(i-1), rs.getObject(i)+"");
                }
            }
            list.add(rowData);
        }
        return list;
    }



}
