package com.example.a11699.lib_greendao.db;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Create time 2020/6/20
 * Create Yu
 * description:聊天表
 */
@Entity(nameInDb = "mychat")
public class ChatBean {
    @Id(autoincrement = true)
    @Unique
    Long id;  //主键自增长，不可重复,作为不同记录对象的标识，传入参数对象时不要传入
    @Property(nameInDb = "sandid")
    private String sendid;
    @Property(nameInDb = "receiveid")
    private String receiveid;
    @Property(nameInDb = "chatcontent")
    private String chatcontent;


    @Generated(hash = 769841883)
    public ChatBean(Long id, String sendid, String receiveid, String chatcontent) {
        this.id = id;
        this.sendid = sendid;
        this.receiveid = receiveid;
        this.chatcontent = chatcontent;
    }

    @Generated(hash = 1872716502)
    public ChatBean() {
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSendid() {
        return this.sendid;
    }

    public void setSendid(String sendid) {
        this.sendid = sendid;
    }

    public String getReceiveid() {
        return this.receiveid;
    }

    public void setReceiveid(String receiveid) {
        this.receiveid = receiveid;
    }

    public String getChatcontent() {
        return this.chatcontent;
    }

    public void setChatcontent(String chatcontent) {
        this.chatcontent = chatcontent;
    }
}
