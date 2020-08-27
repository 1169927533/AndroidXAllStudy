package com.example.a11699.comp_base.bean;

import java.io.Serializable;

/**
 * Create time 2020/1/22
 * Create Yu
 */
public class DailyStudentBean implements Serializable {

    /**
     * address : 3C212
     * baseInfo : 奥术大师多
     * date : 2020-01-20 15:55:00
     * id : 9
     * phone : 1231232
     * room : 早-7-509
     * sid : 201602150131
     * sname : 张松韬
     * solution : 解决办法
     * stuCondition : 谈心情况
     * tid : 03764
     * tname : 冯志林
     * type : 面对面
     * image:
     */

    private String address;
    private String baseInfo;
    private String date;
    private int id;
    private String phone;
    private String room;
    private String evaluate;
    private String sid;
    private String sname;
    private String solution;
    private String stuCondition;
    private String tid;
    private String tname;
    private String type;
    private String imageUrl;
    private String simage;
    private String sex;




    public String getSimage() {
        return simage;
    }

    public void setSimage(String simage) {
        this.simage = simage;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBaseInfo() {
        return baseInfo;
    }

    public void setBaseInfo(String baseInfo) {
        this.baseInfo = baseInfo;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getSid() {
        return sid;
    }

    public String getEvaluate() {
        return evaluate;
    }

    public void setEvaluate(String evaluate) {
        this.evaluate = evaluate;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public String getSname() {
        return sname;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSolution() {
        return solution;
    }

    public void setSolution(String solution) {
        this.solution = solution;
    }

    public String getStuCondition() {
        return stuCondition;
    }

    public void setStuCondition(String stuCondition) {
        this.stuCondition = stuCondition;
    }

    public String getTid() {
        return tid;
    }

    public void setTid(String tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
