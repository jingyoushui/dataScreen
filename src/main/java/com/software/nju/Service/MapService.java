package com.software.nju.Service;

import com.software.nju.Bean.Map;
import com.software.nju.Dao.MapDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class MapService {

    @Autowired
    MapDao mapDao;

    public List<Map> findAll(int current,int size){
        return mapDao.findAll();


    }
    public Map findMapDetail(String id){
        return mapDao.findMapById(id);
    }

    public String save(Map map){
       return mapDao.save(map).getId();
    }

    public String remove(String id){
        return mapDao.removeMapById(id);
    }

}
