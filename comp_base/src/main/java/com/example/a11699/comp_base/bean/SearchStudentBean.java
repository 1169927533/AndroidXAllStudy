package com.example.a11699.comp_base.bean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Create time 2020/2/16
 * Create Yu
 */
public class SearchStudentBean {

    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean implements Serializable {
        /**
         * sname : 毕嘉浩
         * sid : 201604070101
         */

        private String sname;
        private String sid;
        private String simage;
        private String sex;
        private String phone;
        private String sClass;

        public String getsClass() {
            return sClass;
        }

        public void setsClass(String sClass) {
            this.sClass = sClass;
        }

        public String getSex() {
            return sex;
        }

        public void setSex(String sex) {
            this.sex = sex;
        }

        public String getSimage() {
            return simage;
        }

        public void setSimage(String simage) {
            this.simage = simage;
        }

        public String getSname() {
            return sname;
        }

        public void setSname(String sname) {
            this.sname = sname;
        }

        public String getSid() {
            return sid;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setSid(String sid) {
            this.sid = sid;
        }
    }
}
