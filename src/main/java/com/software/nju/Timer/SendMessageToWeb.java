package com.software.nju.Timer;

import com.software.nju.Service.SendMessageSercice;
import com.software.nju.WebSocket.WebsocketServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;


@Slf4j
@Configuration
@EnableScheduling
public class SendMessageToWeb {
    @Autowired
    SendMessageSercice sendMessageSercice;

    @Scheduled(cron = "0 0/1 * * * ?")
    public void sendTestMessage() {
        if(WebsocketServerEndpoint.websocketServerSet.size()>0){
            log.info("发送数据");
            try{
                sendMessageSercice.sendDataToWeb();
            }catch (Exception e){
                e.printStackTrace();
            }


        }
    }


}
