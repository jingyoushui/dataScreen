package com.software.nju.Timer;

import com.software.nju.Bean.SqlBean;
import com.software.nju.Service.SqlBeanService;
import com.software.nju.SqlController.SqlController;
import com.software.nju.SqlController.SqlService;
import com.software.nju.WebSocket.WebsocketServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Configuration
@EnableScheduling
public class SendMessageToWeb {

    @Autowired
    SqlService sqlService;

    @Autowired
    SqlBeanService sqlBeanService;

    @Scheduled(cron = "0/10 * * * * ?")
    public void sendTestMessage() throws SQLException {
        if(WebsocketServerEndpoint.websocketServerSet.size()>0){
            log.info("发送数据");
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
}
