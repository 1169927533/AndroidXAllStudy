package com.example.a11699.comp_chat.bean;

import com.chad.library.adapter.base.entity.MultiItemEntity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Unique;
import org.greenrobot.greendao.annotation.Generated;

/**
 * Create time 2020/6/24
 * Create Yu
 * description:
 */
@Entity
public class ChatBean implements MultiItemEntity {


    @Override
    public int getItemType() {
        return whichItem;
    }

    @Id(autoincrement = true)
    @Unique
    Long id;  //主键自增长，不可重复,作为不同记录对象的标识，传入参数对象时不要传入
    @Property(nameInDb = "sendid")
    private String sendid;
    @Property(nameInDb = "sendname")
    private String sendname;
    @Property(nameInDb = "sendimg")
    private String sendimg;
    @Property(nameInDb = "sendtime")
    private String sendtime;
    @Property(nameInDb = "receivetime")
    private String receivetime;
    @Property(nameInDb = "chatcontent")
    private String chatcontent;
    @Property(nameInDb = "whichitem")
    private int whichItem;
    @Property(nameInDb = "receiveid")
    private String receiveid;

    @Property(nameInDb = "receivename")
    private String receivename;
    @Property(nameInDb = "receiveimg")
    private String receiveimg;

    @Generated(hash = 1021063218)
    public ChatBean(Long id, String sendid, String sendname, String sendimg, String sendtime, String receivetime, String chatcontent, int whichItem, String receiveid, String receivename,
            String receiveimg) {
        this.id = id;
        this.sendid = sendid;
        this.sendname = sendname;
        this.sendimg = sendimg;
        this.sendtime = sendtime;
        this.receivetime = receivetime;
        this.chatcontent = chatcontent;
        this.whichItem = whichItem;
        this.receiveid = receiveid;
        this.receivename = receivename;
        this.receiveimg = receiveimg;
    }

    public ChatBean(int whichItem, String sendid, String sendname, String sendimg, String sendtime, String receivetime, String chatcontent, String receiveid, String receivename, String receiveimg) {
        this.whichItem = whichItem;
        this.sendid = sendid;
        this.sendname = sendname;
        this.sendimg = sendimg;
        this.sendtime = sendtime;
        this.receivetime = receivetime;
        this.chatcontent = chatcontent;
        this.receiveid = receiveid;
        this.receivename = receivename;
        this.receiveimg = receiveimg;
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

    public String getSendname() {
        return this.sendname;
    }

    public void setSendname(String sendname) {
        this.sendname = sendname;
    }

    public String getSendimg() {
        return this.sendimg;
    }

    public void setSendimg(String sendimg) {
        this.sendimg = sendimg;
    }

    public String getSendtime() {
        return this.sendtime;
    }

    public void setSendtime(String sendtime) {
        this.sendtime = sendtime;
    }

    public String getReceivetime() {
        return this.receivetime;
    }

    public void setReceivetime(String receivetime) {
        this.receivetime = receivetime;
    }

    public String getChatcontent() {
        return this.chatcontent;
    }

    public void setChatcontent(String chatcontent) {
        this.chatcontent = chatcontent;
    }

    public String getReceiveid() {
        return this.receiveid;
    }

    public void setReceiveid(String receiveid) {
        this.receiveid = receiveid;
    }

    public String getReceivename() {
        return this.receivename;
    }

    public void setReceivename(String receivename) {
        this.receivename = receivename;
    }

    public String getReceiveimg() {
        return this.receiveimg;
    }

    public void setReceiveimg(String receiveimg) {
        this.receiveimg = receiveimg;
    }

    public int getWhichItem() {
        return this.whichItem;
    }

    public void setWhichItem(int whichItem) {
        this.whichItem = whichItem;
    }
}
