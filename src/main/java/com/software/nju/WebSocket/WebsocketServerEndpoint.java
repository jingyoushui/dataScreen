package com.software.nju.WebSocket;

import com.alibaba.druid.support.json.JSONUtils;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArraySet;

@Slf4j
@Component
@ServerEndpoint(value = "/websocket/{id}",encoders = {ServerEncoder.class})
public class WebsocketServerEndpoint {

    //在线连接数,应该把它设计成线程安全的
    private static int onlineCount = 0;

    //concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
    //虽然@Component默认是单例模式的，但springboot还是会为每个websocket连接初始化一个bean，所以可以用一个静态set保存起来。
    public static CopyOnWriteArraySet<WebsocketServerEndpoint> websocketServerSet
            = new CopyOnWriteArraySet<>();

    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private Session session;

    //会话窗口的ID标识
    private String id = "";

    /**
     * 链接成功调用的方法
     *
     * @param session
     * @param id
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("id") String id) {
        log.info("onOpen >> 链接成功");
        this.session = session;

        //将当前websocket对象存入到Set集合中
        websocketServerSet.add(this);

        //在线人数+1
        addOnlineCount();

        log.info("有新窗口开始监听：" + id + ", 当前在线人数为：" + getOnlineCount());

        this.id = id;

        try {
            sendMessage("有新窗口开始监听：" + id + ", 当前在线人数为：" + getOnlineCount());

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

    /**
     * 链接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        log.info("onClose >> 链接关闭");

        //移除当前Websocket对象
        websocketServerSet.remove(this);

        //在内线人数-1
        subOnLineCount();

        log.info("链接关闭，当前在线人数：" + getOnlineCount());
    }

    /**
     * 收到客户端消息后调用的方法
     *
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("接收到窗口：" + id + " 的信息：" + message);

        //发送信息
        for (WebsocketServerEndpoint websocketServerEndpoint : websocketServerSet) {
            try {
                websocketServerEndpoint.sendMessage("接收到窗口：" + id + " 的信息：" + message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable e) {
        e.printStackTrace();
    }

    /**
     * 推送消息
     *
     * @param message
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    private void sendObject(Object object) throws IOException, EncodeException {
        this.session.getBasicRemote().sendObject(object);
    }

    /**
     * 自定义推送消息
     *　id为null时是群发
     * @param message
     * @param id
     */
    public static void sendInfo(String id, String message) {
        if(id==null){
            log.info( "群发数据，推送内容：" + message);
        }
        for (WebsocketServerEndpoint endpoint : websocketServerSet) {
            try {
                if (id == null) {
                    endpoint.sendMessage(message);
                } else if (endpoint.id.equals(id)) {
                    log.info("推送消息到窗口：" + id + " ，推送内容：" + message);
                    endpoint.sendMessage(message);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }


    /**
     * 自定义推送消息
     *　id为null时是群发
     * @param map
     * @param id
     */
    public static void sendData(String id, Object map) {
        for (WebsocketServerEndpoint endpoint : websocketServerSet) {
            try {
                if (id == null) {
                    endpoint.sendObject(map);
                } else if (endpoint.id.equals(id)) {
//                    log.info("推送消息到窗口：" + id + " ，推送内容：" + map);
                    String str= JSON.toJSON(map).toString();
                    System.out.println(str);
                    endpoint.sendObject(str);
                }
            } catch (Exception e) {
                e.printStackTrace();
                continue;
            }
        }
    }

    private void subOnLineCount() {
        WebsocketServerEndpoint.onlineCount--;
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    private void addOnlineCount() {
        WebsocketServerEndpoint.onlineCount++;
    }
}