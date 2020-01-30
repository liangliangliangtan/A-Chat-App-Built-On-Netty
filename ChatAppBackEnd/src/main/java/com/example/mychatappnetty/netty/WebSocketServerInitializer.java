package com.example.mychatappnetty.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

public class WebSocketServerInitializer extends ChannelInitializer<SocketChannel> {

    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();
        pipeline.addLast(new HttpServerCodec()); // Websocket is based on http protocol, so HttpServerCodec is neccesary.

        pipeline.addLast(new ChunkedWriteHandler()); // support for writing a large data stream
        // aggregate Http Message as FullHttpRequest, FullHttpResponse
        // Almost in netty programs, it is useful.
        pipeline.addLast(new HttpObjectAggregator(1024*64));


        //============ before are handlers to support HTTP protocol;


        // websocket server protocol, it defines the router for clients to visit.
        // it will handle heavy lifts for you such as handshake( close, PING/PONG)
        // In websocket, data are transmitted by frames.
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));


        // Customized handler;
        pipeline.addLast(new ChatHandler());
    }
}
