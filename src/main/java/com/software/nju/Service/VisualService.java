package com.software.nju.Service;

import com.software.nju.Bean.Visual;
import com.software.nju.Dao.VisualDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Service
public class VisualService {
    @Autowired
    VisualDao visualDao;

    public Page<Visual> findVisualByCategory(int category, Pageable pageable){
        return visualDao.findVisualsByCategory(category,  pageable);
    }

    public Visual findVisualById(int id){
        return visualDao.findVisualById(id);
    }

    public Integer saveVisual(Visual visual){
        return visualDao.save(visual).getId();
    }
    public Integer remove(Integer id){
        return visualDao.removeVisualById(id);
    }
}
