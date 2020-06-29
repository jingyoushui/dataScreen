package com.software.nju.Service;

import com.software.nju.Bean.Category;
import com.software.nju.Dao.CategoryDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryService {

    @Autowired
    CategoryDao categoryDao;


    public List<Category> findAll(){
        return categoryDao.findAll();
    }
    public String save(Category category){
        return categoryDao.save(category).getId();
    }
    public Category getCategory(String id){
        return categoryDao.getCategoryById(id);
    }

}
