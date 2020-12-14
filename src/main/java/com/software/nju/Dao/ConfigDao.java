package com.software.nju.Dao;

import com.software.nju.Bean.Config;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConfigDao extends JpaRepository<Config,Integer> {

    Config findConfigById(int id);

    Config findConfigByVisualId(int visualId);
    Integer removeConfigById(int id);
}
