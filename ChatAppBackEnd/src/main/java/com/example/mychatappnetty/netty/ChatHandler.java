package com.example.mychatappnetty.netty;
import com.example.mychatappnetty.enums.MsgActionEnum;
import com.example.mychatappnetty.netty.entity.ChatMsg;
import com.example.mychatappnetty.netty.entity.DataContent;
import com.example.mychatappnetty.service.ChatService;
import com.example.mychatappnetty.util.JsonUtils;
import com.example.mychatappnetty.SpringUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * deal with text message handler.
 * TextWebSocketFrame:　it is the object for dealing with text in web socket in netty .
 * Frame is the carrier/medium of  message
 */
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {



    private static ChannelGroup users = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // String content = msg.text(); //get client message from textWebSocketFrame.
        /*System.out.println("received message is: " + content);
        users.forEach(channel ->
            channel.writeAndFlush(new TextWebSocketFrame("[Server get message at:]+" +
                    LocalDateTime.now() +
                    ",Message content" + content))
        );*/

        /*
        * 1. get  messages from client.
        *
        * 2. justify message type. Different Type has different business logic
        *
        * 2.1 when a websocket connection is open, init the channel and associate user with its user id
        *
        * 2.2 Send message to netty , save the message to database and mark it as unread.
        *
        * 2.3 Read message to netty, marked it as read.
        *
        * 2.4 HeartBeat ( 1.Browser 2. Netty )
        * */
        Channel currentChannel = ctx.channel();

        String content = msg.text(); //get client message from textWebSocketFrame.
        DataContent dataContent = JsonUtils.jsonToPojo(content,DataContent.class);
        ChatService chatService = (ChatService) SpringUtil.getBean("chatServiceImpl");
        RedisTemplate redisTemplate = (RedisTemplate)SpringUtil.getBean("redisTemplate");

        int action = dataContent.getAction();
        if (action== MsgActionEnum.CONNECT.type){
            String senderId = dataContent.getChatMsg().getSenderId();
            // Get the redisTemplate, and put it as a key-value pair in the database;
            redisTemplate.opsForValue().set(senderId, currentChannel.id().asLongText());


            /*// Print Channel with channel id for testing
            System.out.println("print all channel ids in the group");
            for(Channel channel: users){
                System.out.println(channel.id().asLongText());
            }

            // Iterate all key-values pairs (userId - channel:Id）；
            Set<String> keys = redisTemplate.keys("*");// you can use any specific pattern of key
            Iterator<String> it = keys.iterator();
            while (it.hasNext()) {
                String userId = it.next();
                String channelId = (String)redisTemplate.opsForValue().get(userId);
                if (StringUtils.isNotBlank(channelId)) {
                    System.out.println(userId + "#" + channelId);
                }
            }*/

        }else if (action== MsgActionEnum.CHAT.type ){
            ChatMsg chatMsg = dataContent.getChatMsg();

            // convert chatMsg content to chatObject in order to save to the database.
            com.example.mychatappnetty.entity.ChatMsg chatObj = new com.example.mychatappnetty.entity.ChatMsg();

            chatObj.setChatMsg(chatMsg.getMsg());
            chatObj.setAcceptUserId(chatMsg.getReceiverId());
            chatObj.setSendUserId(chatMsg.getSenderId());


            String msgId = chatService.saveMsg(chatObj);
            chatMsg.setMsgId(msgId);
            chatMsg.setMsg(chatObj.getChatMsg());
            String receiverChannelLongId = (String) redisTemplate.opsForValue().get(chatMsg.getReceiverId());


            if (receiverChannelLongId == null){
                // User is not connected to the server, Push notifications
                // TODO: Push notifications
            }else{

                // find channel by its long id
                Channel findChannel = null;
                for(Channel channel: users){
                    if (channel.id().asLongText().equals(receiverChannelLongId)){
                        findChannel = channel;
                    }
                }

                if (findChannel != null){
                    // user is online, send message to that user.
                    DataContent dataContentMsg = new DataContent();
                    dataContentMsg.setChatMsg(chatMsg);
                    findChannel.writeAndFlush(new TextWebSocketFrame(JsonUtils.objectToJson(dataContentMsg)));
                } else {
                    // user is offline, Push notifications
                    // TODO: Push notifications
                }
            }

        }else if (action == MsgActionEnum.SIGNED.type){

            // 1. message ids that need to be signed, split by comma
            String msgIdsStr = dataContent.getExtend();

            String[] msgIds = msgIdsStr.split(",");

            List<String> msgIdList = new ArrayList<>();

            for (String id : msgIds){
                if (StringUtils.isNotBlank(id)){
                    msgIdList.add(id);
                }
            }

            //
            System.out.println(msgIdsStr);
            chatService.batchUpdateMsgSigned(msgIdList);

        }else if (action == MsgActionEnum.KEEPALIVE.type){

        }
    }

    /**
     * When client has connected to the server
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        //when the client is connected  to the server,
        // get the client channel and add to ChannelGroup for uniform management.
        users.add(ctx.channel());
    }

    /**
     * When client disconnected
     * @param ctx
     * @throws Exception
     */
    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        // when handlerRemoved method is triggered, it will automatically remove from channel group
        String channelId = ctx.channel().id().asShortText();
        System.out.println("Remove client where its channelId：" + channelId);
        users.remove(ctx.channel());
    }


    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {

        // print cause trace and close the channel connection, remove it from the channel group.
        cause.printStackTrace();

        ctx.channel().close();

        users.remove(ctx.channel());

    }

    public static ChannelGroup getUsers() {
        return users;
    }
}
