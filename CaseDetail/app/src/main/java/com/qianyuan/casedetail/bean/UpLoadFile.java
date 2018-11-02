package com.qianyuan.casedetail.bean;

import java.util.List;

/**
 * Created by sun on 2017/5/22.
 */

public class UpLoadFile {


    /**
     * CODE : 1
     * DATA : [{"UUID":"3NK2DNESW3L2YR63"}]
     */

    private int CODE;
    private List<DATABean> DATA;

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public List<DATABean> getDATA() {
        return DATA;
    }

    public void setDATA(List<DATABean> DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * UUID : 3NK2DNESW3L2YR63
         */

        private String UUID;

        public String getUUID() {
            return UUID;
        }

        public void setUUID(String UUID) {
            this.UUID = UUID;
        }
    }
}
