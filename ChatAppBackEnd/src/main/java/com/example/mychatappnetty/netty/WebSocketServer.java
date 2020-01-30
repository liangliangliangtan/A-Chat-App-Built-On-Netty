package com.example.mychatappnetty.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.springframework.stereotype.Component;

@Component
public class WebSocketServer {

    private EventLoopGroup mainGroup;
    private EventLoopGroup subGroup;
    private ServerBootstrap serverBootstrap;
    private ChannelFuture channelFuture;

    private WebSocketServer() {
        this.mainGroup = new NioEventLoopGroup();
        this.subGroup = new NioEventLoopGroup();
        this.serverBootstrap = new ServerBootstrap();
        this.serverBootstrap.group(mainGroup,subGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new WebSocketServerInitializer());
    }

    //WebSocketServer should be singleton
    private static class WebSocketServerCreater {
         private static final WebSocketServer instance = new WebSocketServer();
    }

    public static WebSocketServer getInstance(){
        return WebSocketServerCreater.instance;
    }

    public void start() {
        this.channelFuture = serverBootstrap.bind(8088);
        System.err.println("netty websocket starts successfully");
    }


}
