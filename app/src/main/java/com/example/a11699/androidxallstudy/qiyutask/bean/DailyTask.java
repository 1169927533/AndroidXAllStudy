package com.example.a11699.androidxallstudy.qiyutask.bean;

import java.io.Serializable;
import java.util.List;

public class DailyTask {
    private List<DailyTaskBean> dailyTask;
    private List<DailyTaskBean> onceTask;

    public List<DailyTaskBean> getDailyTask() {
        return dailyTask;
    }

    public void setDailyTask(List<DailyTaskBean> dailyTask) {
        this.dailyTask = dailyTask;
    }

    public List<DailyTaskBean> getOnceTask() {
        return onceTask;
    }

    public void setOnceTask(List<DailyTaskBean> onceTask) {
        this.onceTask = onceTask;
    }

    public static class DailyTaskBean implements Serializable {
        /**
         * flag : 1
         * desc : 完成一次充值(300金币)
         * coin : 300
         * status : 0
         */

        private String flag;
        private String desc;
        private String coin;
        private String exp;
        private String award;
        private int target;
        private String type;
        private int complete;
        /**
         * status 0未完成 1已完成未领取 2已领取
         */
        private int status;
        /**
         * true 为一次任务
         */
        private boolean isOnceTask = false;

        public String getExp() {
            return exp;
        }

        public void setExp(String exp) {
            this.exp = exp;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public boolean isOnceTask() {
            return isOnceTask;
        }

        public void setOnceTask(boolean onceTask) {
            isOnceTask = onceTask;
        }

        public int getComplete() {
            return complete;
        }

        public void setComplete(int complete) {
            this.complete = complete;
        }

        public int getTarget() {
            return target;
        }

        public void setTarget(int target) {
            this.target = target;
        }

        public String getAward() {
            return award;
        }

        public void setAward(String award) {
            this.award = award;
        }

        public String getFlag() {
            return flag;
        }

        public void setFlag(String flag) {
            this.flag = flag;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCoin() {
            return coin;
        }

        public void setCoin(String coin) {
            this.coin = coin;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}