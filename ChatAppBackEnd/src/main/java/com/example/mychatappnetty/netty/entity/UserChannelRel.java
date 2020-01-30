package com.example.mychatappnetty.netty.entity;

import io.netty.channel.Channel;
import java.util.HashMap;
import java.util.Map;

/**
 * @author tan3
 * @ClassName UserChannelRel.java
 * @Description Please enter description here
 * @createTime 2020 -  01 - 23 - 10 : 59
 */
public class UserChannelRel {

    private static Map<String, Channel> manager = new HashMap<>();

    public static void put(String senderId, Channel channel) {
        manager.put(senderId, channel);
    }

    public static Channel get(String senderId) {
        return manager.get(senderId);
    }

    public static void output() {
        for (HashMap.Entry<String, Channel> entry : manager.entrySet()) {
            System.out.println("UserId: " + entry.getKey()
                + ", ChannelId: " + entry.getValue().id().asLongText());
        }
    }

}
