package com.software.nju.Dao;

import com.software.nju.Bean.SqlBean;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SqlBeanDao extends JpaRepository<SqlBean,String> {

    SqlBean findSqlBeanBySqlUrl(String sqlUrl);

    List<SqlBean> findSqlBeansByWebsocketId(String  websocketId);
}
