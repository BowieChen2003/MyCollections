package com.ccb.backend.websocket;


import jakarta.websocket.OnClose;
import jakarta.websocket.OnOpen;
import jakarta.websocket.Session;
import jakarta.websocket.server.PathParam;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/ws/{sid}")
@Slf4j
public class WebSocketServer {
    private static Map<String, Session> sessionMap = new HashMap();

    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid){
        log.info("WebSocket opened with sid: {}", sid);
        sessionMap.put(sid, session);
    }

    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        log.info("连接断开:" + sid);
        sessionMap.remove(sid);
    }

    public void sendMessage(String message, Long userId) {

        String sid = userId.toString();
        Session session = sessionMap.get(sid);

        if (session != null && session.isOpen()) {
            try {
                session.getBasicRemote().sendText(message);
                log.info("消息已发送给用户 {}: {}", userId, message);
            } catch (Exception e) {
                log.error("向用户 {} 发送消息失败", userId, e);
            }
        } else {
            log.warn("用户 {} 不在线或会话已关闭", userId);
        }

//        Collection<Session> sessions = sessionMap.values();
//        for (Session session : sessions) {
//            try {
//                if(session.getId().equals(id)) {
//                    session.getBasicRemote().sendText(message);
//                    return;
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }
//        return;
    }

}
