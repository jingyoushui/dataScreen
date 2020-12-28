package com.software.nju.Vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;
import org.springframework.stereotype.Component;

import javax.persistence.Entity;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Accessors(chain = true)

public class SqlBeanVo {
    private Integer id;

    //该sql对应的接口的地址
    private String sqlUrl;
    //对sql功能的描述
    private String sqlDes;
    //sql对应的参数列表,如1,2,3,4
    private String  paramList;

    private String websocketId;
}
