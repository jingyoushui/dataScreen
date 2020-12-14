package com.software.nju.WebSocket;

import com.alibaba.fastjson.JSON;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;
import java.util.List;

/**
 * 配置WebSocket解码器，用于发送请求的时候可以发送Object对象，实则是json数据
 * sendObject()
 * @ClassNmae：ServerEncoder
 *
 */
public class ServerEncoder implements Encoder.Text<List> {

    @Override
    public void destroy() {
        // TODO Auto-generated method stub

    }

    @Override
    public void init(EndpointConfig arg0) {
        // TODO Auto-generated method stub

    }

    @Override
    public String encode(List list) throws EncodeException {
        try {
            return JSON.toJSON(list).toString();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            return "";
        }
    }

}
