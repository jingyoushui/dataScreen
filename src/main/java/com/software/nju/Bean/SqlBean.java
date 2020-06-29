package com.software.nju.Bean;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.HashMap;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)
@Entity
@Table()
public class SqlBean {
    @Id
    private String id;

    //sql语句
    private String sqlString;
    //该sql对应的接口的地址
    private String sqlUrl;
    //对sql功能的描述
    private String sqlDes;
    //sql对应的参数列表,如1,2,3,4
    private String  paramList;

}
