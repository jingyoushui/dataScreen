package com.software.nju.Service;

import com.software.nju.Bean.SqlBean;
import com.software.nju.Bean.Visual;
import com.software.nju.Dao.SqlBeanDao;
import com.software.nju.Vo.SqlBeanVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SqlBeanService {
    @Autowired
    SqlBeanDao sqlBeanDao;

    public SqlBean getSqlBeanBySqlUrl(String url){
        return sqlBeanDao.findSqlBeanBySqlUrl(url);
    }


    public List<SqlBeanVo> getAll(Pageable pageable){
        Page<SqlBean> list =  sqlBeanDao.findAll(pageable);
        List<SqlBeanVo> res = new ArrayList<>();
        for(SqlBean sqlBean:list){
            res.add(new SqlBeanVo().setId(sqlBean.getId())
                    .setParamList(sqlBean.getParamList()).setSqlDes(sqlBean.getSqlDes())
                    .setSqlUrl(sqlBean.getSqlUrl()).setWebsocketId(sqlBean.getWebsocketId()));
        }
        return res;
    }

    public List<SqlBean> findSqlBeansByWebsocketId(String id){
        return sqlBeanDao.findSqlBeansByWebsocketId(id);
    }

    public Integer save(SqlBeanVo sqlBeanVo){
        SqlBean sqlBean = sqlBeanDao.findSqlBeanById(sqlBeanVo.getId());
        sqlBean.setParamList(sqlBeanVo.getParamList()).setSqlDes(sqlBeanVo.getSqlDes())
                .setSqlUrl(sqlBeanVo.getSqlUrl()).setWebsocketId(sqlBeanVo.getWebsocketId());
        return sqlBeanDao.save(sqlBean).getId();
    }

    public List<SqlBeanVo> findSqlBeanVosByWebsocketId(String id){
        return sqlBeanDao.findSqlBeanVosByWebsocketId(id);
    }

    public int getCount(){
        return sqlBeanDao.getCount();
    }
}
