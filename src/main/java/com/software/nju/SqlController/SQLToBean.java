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


    public Integer getJHNumber(String sql) throws SQLException {
        ResultSet res = SQLHelper.getJHResultSet(sql);
        Map<String, Integer> map = new HashMap<>();
        int number = 0;
        if (res.next()) {
            number = res.getInt(1);
        }
        return number;
    }

    public List<Map<String, String>> getJHList(String sql,List<String> parms) throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();
        ResultSet rs = SQLHelper.getJHResultSet(sql);
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
        ResultSet res = SQLHelper.getGYResultSet(sql);
        Map<String, Integer> map = new HashMap<>();
        int number = 0;
        if (res.next()) {
            number = res.getInt(1);
        }
        return number;
    }

    public List<Map<String, String>> getGYList(String sql,List<String> parms) throws SQLException {
        List<Map<String, String>> list = new ArrayList<>();
        ResultSet rs = SQLHelper.getGYResultSet(sql);
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
