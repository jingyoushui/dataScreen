package com.software.nju.Service;

import com.software.nju.Bean.SqlBean;
import com.software.nju.Service.SqlBeanService;
import com.software.nju.SqlController.SqlService;
import com.software.nju.WebSocket.WebsocketServerEndpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class SendMessageSercice {

    @Autowired
    SqlService sqlService;

    @Autowired
    SqlBeanService sqlBeanService;


    public void sendDataToWeb() throws SQLException {
        List<SqlBean> sqlList = sqlBeanService.getAll();
        List<Object> list = new ArrayList<>();
        for(SqlBean sqlBean:sqlList){
            String sqlApi = sqlBean.getSqlApi();
            String sqlType = sqlBean.getSqlType();
            String sqls = sqlBean.getSqlString();
            String params = sqlBean.getParamList();
            Object o = null;
            if(sqlType.equals("getNumber")){
                o = sqlService.getNumber(sqls,sqlBean.getSqlUrl(),
                        sqlBean.getSqlDes(),params,sqlApi);
            }else if(sqlType.equals("getNumbers")){
                o = sqlService.getNumbers(sqls,sqlBean.getSqlUrl(),
                        sqlBean.getSqlDes(),params,sqlApi);
            }else if(sqlType.equals("getList")){
                o = sqlService.getList(sqls,sqlBean.getSqlUrl(),
                        sqlBean.getSqlDes(),params,sqlApi);
            }
            list.add(o);


        }

        WebsocketServerEndpoint.sendData(null,list);
    }
}
