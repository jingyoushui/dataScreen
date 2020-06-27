package com.software.nju.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;

//@AllArgsConstructor
//@NoArgsConstructor
//@Data
//@Accessors(chain = true)
//@Document()
@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table(name = "blade_visual_config")
public class Config implements Serializable {

    @Id
    private Integer id;
    //可视化表主键
    private Integer visualId;
    //配置
    private String detail;
    //组件
    private String component;

}
