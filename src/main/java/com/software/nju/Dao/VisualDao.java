package com.software.nju.Dao;

import com.software.nju.Bean.Visual;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Repository
public interface VisualDao extends JpaRepository<Visual,String> {

    Page<Visual> findVisualsByCategory(int category, Pageable pageable);
    Visual findVisualById(int id);

    Integer removeVisualById(int id);

    @Query(value = "select count(*) from blade_visual where category=?1",nativeQuery = true)
    int getCount(int category);

}
