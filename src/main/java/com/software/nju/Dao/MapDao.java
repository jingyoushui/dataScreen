package com.software.nju.Dao;

import com.software.nju.Bean.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public interface MapDao extends JpaRepository<Map,String> {
    Map findMapById(String id);
    String removeMapById(String id);


}
