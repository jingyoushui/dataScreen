package com.software.nju.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name="blade_visual_category")
//可视化分类表
public class Category implements Serializable {
    @Id
    //主键id
    private String id;
    //分类键值
    private String categoryKey;
    //分类名称
    private String categoryValue;
    //是否删除
    private Integer isDeleted;

}
