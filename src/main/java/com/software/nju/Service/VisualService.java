package com.software.nju.Service;

import com.software.nju.Bean.Visual;
import com.software.nju.Dao.VisualDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class VisualService {
    @Autowired
    VisualDao visualDao;

    public List<Visual> findVisualByCategory(int category){
        return visualDao.findVisualsByCategory(category);
    }

    public Visual findVisualById(int id){
        return visualDao.findVisualById(id);
    }

    public Integer saveVisual(Visual visual){
        return visualDao.save(visual).getId();
    }
}
