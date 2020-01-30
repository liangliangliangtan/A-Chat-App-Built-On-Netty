package com.example.mychatappnetty;

import com.example.mychatappnetty.netty.WebSocketServer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class NettyBooter implements ApplicationListener<ContextRefreshedEvent> {

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        if(contextRefreshedEvent.getApplicationContext().getParent() == null ){
            try{
                WebSocketServer.getInstance().start();
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
