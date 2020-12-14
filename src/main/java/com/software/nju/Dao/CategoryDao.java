package com.software.nju.Dao;

import com.software.nju.Bean.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface CategoryDao extends JpaRepository<Category,String> {

    Category getCategoryById(String id);

    String removeCategoryById(String id);
}
