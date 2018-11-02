package com.qianyuan.casedetail.bean;

import java.util.List;

/**
 * Created by sun on 2017/5/11.
 */

public class HandleDetailBean {


    /**
     * CODE : 1
     * DATA : [{"PARENTUUID":"P2N26ARJ8DPZ4R9B","STATE":"结案","STATETYPE":1,"DATETIME":"2017/6/7 12:54:16","USERNAME":"sun","DEPARTNAME":"测试组","MEMO":null},{"PARENTUUID":"P2N26ARJ8DPZ4R9B","STATE":"核查","STATETYPE":1,"DATETIME":"2017/6/7 12:54:16","USERNAME":"sun","DEPARTNAME":"测试组","MEMO":"sun 已核查"},{"PARENTUUID":"P2N26ARJ8DPZ4R9B","STATE":"核查","STATETYPE":0,"DATETIME":"2017/6/7 12:53:33","USERNAME":"sun","DEPARTNAME":"测试组","MEMO":null},{"PARENTUUID":"P2N26ARJ8DPZ4R9B","STATE":"处置","STATETYPE":1,"DATETIME":"2017/6/7 12:53:00","USERNAME":"sun","DEPARTNAME":"测试组","MEMO":"处置完毕"},{"PARENTUUID":"P2N26ARJ8DPZ4R9B","STATE":"处置","STATETYPE":0,"DATETIME":"2017/6/7 12:52:22","USERNAME":"sun","DEPARTNAME":"测试组","MEMO":"可以处置"},{"PARENTUUID":"P2N26ARJ8DPZ4R9B","STATE":"核实","STATETYPE":1,"DATETIME":"2017/6/7 12:51:48","USERNAME":"sun","DEPARTNAME":"测试组","MEMO":"sun 已核实为真"},{"PARENTUUID":"P2N26ARJ8DPZ4R9B","STATE":"核实","STATETYPE":0,"DATETIME":"2017/6/7 11:34:46","USERNAME":"sun","DEPARTNAME":"测试组","MEMO":"sun 已派发核实"},{"PARENTUUID":"P2N26ARJ8DPZ4R9B","STATE":"受理","STATETYPE":1,"DATETIME":"2017/6/7 11:34:25","USERNAME":"sun","DEPARTNAME":"测试组","MEMO":"可以受理"},{"PARENTUUID":"P2N26ARJ8DPZ4R9B","STATE":"上报","STATETYPE":1,"DATETIME":"2017/6/7 10:09:35","USERNAME":"sun","DEPARTNAME":"测试组","MEMO":"sun上报案件"}]
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
         * PARENTUUID : P2N26ARJ8DPZ4R9B
         * STATE : 结案
         * STATETYPE : 1
         * DATETIME : 2017/6/7 12:54:16
         * USERNAME : sun
         * DEPARTNAME : 测试组
         * MEMO : null
         */

        private String PARENTUUID;
        private String STATE;
        private int STATETYPE;
        private String DATETIME;
        private String USERNAME;
        private String DEPARTNAME;
        private Object MEMO;

        public String getPARENTUUID() {
            return PARENTUUID;
        }

        public void setPARENTUUID(String PARENTUUID) {
            this.PARENTUUID = PARENTUUID;
        }

        public String getSTATE() {
            return STATE;
        }

        public void setSTATE(String STATE) {
            this.STATE = STATE;
        }

        public int getSTATETYPE() {
            return STATETYPE;
        }

        public void setSTATETYPE(int STATETYPE) {
            this.STATETYPE = STATETYPE;
        }

        public String getDATETIME() {
            return DATETIME;
        }

        public void setDATETIME(String DATETIME) {
            this.DATETIME = DATETIME;
        }

        public String getUSERNAME() {
            return USERNAME;
        }

        public void setUSERNAME(String USERNAME) {
            this.USERNAME = USERNAME;
        }

        public String getDEPARTNAME() {
            return DEPARTNAME;
        }

        public void setDEPARTNAME(String DEPARTNAME) {
            this.DEPARTNAME = DEPARTNAME;
        }

        public Object getMEMO() {
            return MEMO;
        }

        public void setMEMO(Object MEMO) {
            this.MEMO = MEMO;
        }
    }
}
