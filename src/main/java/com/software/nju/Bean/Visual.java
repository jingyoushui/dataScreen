package com.software.nju.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "blade_visual")
//可视化表
public class Visual implements Serializable {

    @Id
    //主键id
    private Integer id;
    //大屏标题
    private String title;
    //大屏背景图
    private String backgroundUrl;
    //大屏类型
    private Integer category;
    //发布密码
    private String password;
    //创建人id
    private Integer createUser;
    //创建部门
    private Integer createDept;
    //创建时间
    private Date createTime;
    //修改人id
    private Integer updateUser;
    //修改时间
    private Date updateTime;
    //状态
    private Integer status;
    //是否删除
    private Integer isDeleted;



}
