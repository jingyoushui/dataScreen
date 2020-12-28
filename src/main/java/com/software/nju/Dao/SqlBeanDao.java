package com.software.nju.Dao;

import com.software.nju.Bean.SqlBean;
import com.software.nju.Vo.SqlBeanVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface SqlBeanDao extends JpaRepository<SqlBean,String> {

    SqlBean findSqlBeanBySqlUrl(String sqlUrl);

    SqlBean findSqlBeanById(Integer id);

    List<SqlBean> findSqlBeansByWebsocketId(String  websocketId);

    List<SqlBeanVo> findSqlBeanVosByWebsocketId(String websocketId);

    @Query(value = "select count(*) from sql_bean",nativeQuery = true)
    int getCount();



}
