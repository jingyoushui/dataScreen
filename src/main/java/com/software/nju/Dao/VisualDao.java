package com.software.nju.Dao;

import com.software.nju.Bean.Visual;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VisualDao extends JpaRepository<Visual,String> {
    List<Visual> findVisualsByCategory(int category);
    Visual findVisualById(int id);

}
