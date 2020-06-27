package com.software.nju.Service;

import com.software.nju.Bean.Map;
import com.software.nju.Dao.MapDao;
import com.software.nju.Model.MapData;
import com.software.nju.Model.MapModel;
import com.software.nju.Model.MongoPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import javax.annotation.Resource;
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

}
