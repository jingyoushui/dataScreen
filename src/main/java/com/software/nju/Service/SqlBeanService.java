package com.software.nju.Service;

import com.software.nju.Bean.SqlBean;
import com.software.nju.Dao.SqlBeanDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SqlBeanService {
    @Autowired
    SqlBeanDao sqlBeanDao;

    public SqlBean getSqlBeanBySqlUrl(String url){
        return sqlBeanDao.findSqlBeanBySqlUrl(url);
    }
    public List<SqlBean> getAll(){
        return sqlBeanDao.findAll();
    }

    public List<SqlBean> findSqlBeansByWebsocketId(String id){
        return sqlBeanDao.findSqlBeansByWebsocketId(id);
    }
}
