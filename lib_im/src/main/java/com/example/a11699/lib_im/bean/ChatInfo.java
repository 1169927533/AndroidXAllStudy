package com.example.a11699.lib_im.bean;

import com.tencent.imsdk.v2.V2TIMConversation;

import java.io.Serializable;

/**
 * 聊天信息基本类
 */
public class ChatInfo implements Serializable {

    private String chatName;
    private int messageType = V2TIMConversation.V2TIM_C2C;
    private String id;
    private boolean isTopChat;

    public ChatInfo() {

    }

    /**
     * 获取聊天的标题，单聊一般为对方名称，群聊为群名字
     *
     * @return
     */
    public String getChatName() {
        return chatName;
    }

    /**
     * 设置聊天的标题，单聊一般为对方名称，群聊为群名字
     *
     * @param chatName
     */
    public void setChatName(String chatName) {
        this.chatName = chatName;
    }

    /**
     * 获取聊天类型，C2C为单聊，Group为群聊
     *
     * @return
     */
    public int getMessageType() {
        return messageType;
    }

    /**
     * 设置聊天类型，C2C为单聊，Group为群聊
     *
     * @param messageType
     */
    public void setMessageType(int messageType) {
        this.messageType = messageType;
    }

    /**
     * 获取聊天唯一标识
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     * 设置聊天唯一标识
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 是否为置顶的会话
     *
     * @return
     */
    public boolean isTopChat() {
        return isTopChat;
    }

    /**
     * 设置会话是否置顶
     *
     * @param topChat
     */
    public void setTopChat(boolean topChat) {
        isTopChat = topChat;
    }

}
