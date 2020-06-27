package com.software.nju.Dao;

import com.software.nju.Bean.Map;
import com.software.nju.Model.MapModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import javax.swing.*;
import java.util.List;

@Repository
public interface MapDao extends JpaRepository<Map,String> {
    Map findMapById(String id);



}
