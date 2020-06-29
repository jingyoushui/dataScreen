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

    Connection connection = SQLHelper.getConnection();

//    public Map<String, Integer> getCount(String sql) throws SQLException {
//        System.out.println(sql);
//        ResultSet res = SQLHelper.getResultSet(sql);
//        Map<String, Integer> map = new HashMap<>();
//        int count = 0;
//        if (res.next()) {
//            count = res.getInt(1);
//        }
//        map.put("value", count);
//        return map;
//    }
    public Integer getNumber(String sql) throws SQLException {
        System.out.println(sql);
        ResultSet res = SQLHelper.getResultSet(sql);
        Map<String, Integer> map = new HashMap<>();
        int number = 0;
        if (res.next()) {
            number = res.getInt(1);
        }
        return number;
    }

    public List<HashMap<String, String>> getList(String sql) throws SQLException {
        System.out.println(sql);
        List<HashMap<String, String>> list = new ArrayList<>();
        ResultSet rs = SQLHelper.getResultSet(sql);
        ResultSetMetaData md = rs.getMetaData();
        int columnCount = md.getColumnCount(); //Map rowData;

        while (rs.next()) { //rowData = new HashMap(columnCount);
            HashMap rowData = new HashMap<String, String>();
            for (int i = 1; i <= columnCount; i++) {
                rowData.put(md.getColumnName(i), rs.getObject(i)+"");
            }
            list.add(rowData);
        }
        return list;
    }
}
