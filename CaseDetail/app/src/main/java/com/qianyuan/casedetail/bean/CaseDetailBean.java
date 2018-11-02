package com.qianyuan.casedetail.bean;

import java.util.List;

/**
 * Created by sun on 2017/5/10.
 */

public class CaseDetailBean {


    /**
     * CODE : 1
     * DATA : {"CASEID":"TF528NPBHVLF8QEC","CASETIME":"0天0小时16分钟","CASETYPE":"部件-公共设施-上水井盖-位移","CZSTATE":1,"EMERGENCYSTATE":"非紧急","HCSTATE":0,"HSSTATE":0,"PICTUREID":[{"ID":"DMR2V5XDEQ8DSJE5","URL":"/Cms_Data/Contents/testFS/Folders/ANJIANFolder/FS_ANJIANBLOB/~contents/DMR2V5XDEQ8DSJE5/compressPic.jpg"}],"STATE":"上报","UPLOADPOSITION":"北京市朝阳区安贞街道裕民路四号院","UPLOADTIME":"2017/11/20 16:34:12","USERID":"DF16514B831E0E10ABEE74C6AC8A4FB27CFCE1EF","WORDDETAIL":"有位老人摔倒了"}
     */

    private int CODE;
    private DATABean DATA;

    public int getCODE() {
        return CODE;
    }

    public void setCODE(int CODE) {
        this.CODE = CODE;
    }

    public DATABean getDATA() {
        return DATA;
    }

    public void setDATA(DATABean DATA) {
        this.DATA = DATA;
    }

    public static class DATABean {
        /**
         * CASEID : TF528NPBHVLF8QEC
         * CASETIME : 0天0小时16分钟
         * CASETYPE : 部件-公共设施-上水井盖-位移
         * CZSTATE : 1
         * EMERGENCYSTATE : 非紧急
         * HCSTATE : 0
         * HSSTATE : 0
         * PICTUREID : [{"ID":"DMR2V5XDEQ8DSJE5","URL":"/Cms_Data/Contents/testFS/Folders/ANJIANFolder/FS_ANJIANBLOB/~contents/DMR2V5XDEQ8DSJE5/compressPic.jpg"}]
         * STATE : 上报
         * UPLOADPOSITION : 北京市朝阳区安贞街道裕民路四号院
         * UPLOADTIME : 2017/11/20 16:34:12
         * USERID : DF16514B831E0E10ABEE74C6AC8A4FB27CFCE1EF
         * WORDDETAIL : 有位老人摔倒了
         */

        private String CASEID;
        private String CASETIME;
        private String CASETYPE;
        private int CZSTATE;
        private String EMERGENCYSTATE;
        private int HCSTATE;
        private int HSSTATE;
        private String STATE;
        private String UPLOADPOSITION;
        private String UPLOADTIME;
        private String USERID;
        private String WORDDETAIL;
        private List<PICTUREIDBean> PICTUREID;
        private List<AUDIOIDBean> AUDIOID;

        public String getCASEID() {
            return CASEID;
        }

        public void setCASEID(String CASEID) {
            this.CASEID = CASEID;
        }

        public String getCASETIME() {
            return CASETIME;
        }

        public void setCASETIME(String CASETIME) {
            this.CASETIME = CASETIME;
        }

        public String getCASETYPE() {
            return CASETYPE;
        }

        public void setCASETYPE(String CASETYPE) {
            this.CASETYPE = CASETYPE;
        }

        public int getCZSTATE() {
            return CZSTATE;
        }

        public void setCZSTATE(int CZSTATE) {
            this.CZSTATE = CZSTATE;
        }

        public String getEMERGENCYSTATE() {
            return EMERGENCYSTATE;
        }

        public void setEMERGENCYSTATE(String EMERGENCYSTATE) {
            this.EMERGENCYSTATE = EMERGENCYSTATE;
        }

        public int getHCSTATE() {
            return HCSTATE;
        }

        public void setHCSTATE(int HCSTATE) {
            this.HCSTATE = HCSTATE;
        }

        public int getHSSTATE() {
            return HSSTATE;
        }

        public void setHSSTATE(int HSSTATE) {
            this.HSSTATE = HSSTATE;
        }

        public String getSTATE() {
            return STATE;
        }

        public void setSTATE(String STATE) {
            this.STATE = STATE;
        }

        public String getUPLOADPOSITION() {
            return UPLOADPOSITION;
        }

        public void setUPLOADPOSITION(String UPLOADPOSITION) {
            this.UPLOADPOSITION = UPLOADPOSITION;
        }

        public String getUPLOADTIME() {
            return UPLOADTIME;
        }

        public void setUPLOADTIME(String UPLOADTIME) {
            this.UPLOADTIME = UPLOADTIME;
        }

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
        }

        public String getWORDDETAIL() {
            return WORDDETAIL;
        }

        public void setWORDDETAIL(String WORDDETAIL) {
            this.WORDDETAIL = WORDDETAIL;
        }

        public List<PICTUREIDBean> getPICTUREID() {
            return PICTUREID;
        }

        public void setPICTUREID(List<PICTUREIDBean> PICTUREID) {
            this.PICTUREID = PICTUREID;
        }
        public List<AUDIOIDBean> getAUDIOID() {
            return AUDIOID;
        }

        public void setAUDIOID(List<AUDIOIDBean> AUDIOID) {
            this.AUDIOID = AUDIOID;
        }

        public static class PICTUREIDBean {
            /**
             * ID : DMR2V5XDEQ8DSJE5
             * URL : /Cms_Data/Contents/testFS/Folders/ANJIANFolder/FS_ANJIANBLOB/~contents/DMR2V5XDEQ8DSJE5/compressPic.jpg
             */

            private String ID;
            private String URL;

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }

            public String getURL() {
                return URL;
            }

            public void setURL(String URL) {
                this.URL = URL;
            }
        }
        public static class AUDIOIDBean {
            /**
             * URL : /Cms_Data/Contents/Fangshan/Folders/ANJIANFolder/FS_ANJIANBLOB/~contents/FZQJYUTFNR7JVDSZ/1494234809884.3gp
             * ID : FZQJYUTFNR7JVDSZ
             */

            private String URL;
            private String ID;

            public String getURL() {
                return URL;
            }

            public void setURL(String URL) {
                this.URL = URL;
            }

            public String getID() {
                return ID;
            }

            public void setID(String ID) {
                this.ID = ID;
            }
        }
    }
}
