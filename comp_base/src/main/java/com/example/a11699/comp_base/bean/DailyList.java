package com.example.a11699.comp_base.bean;

import java.util.ArrayList;
import java.util.List;

/**
 * Create time 2020/1/22
 * Create Yu
 */
public class DailyList {
  public   List<DailyStudentBean> result = new ArrayList<>();

    public List<DailyStudentBean> getList() {
        return result;
    }

    public void setList(List<DailyStudentBean> list) {
        this.result = list;
    }
}
