package com.qianyuan.casedetail.bean;

import java.util.List;

/**
 * Created by sun on 2017/5/11.
 */

public class EndCaseBean {


    /**
     * CODE : 1
     * DATA : {"USERID":"CAF84AB36897B725E25758E0D5B2387CA93697EE","CASEID":"ZZKJS64SV8NA82CZ","UPLOADTIME":"2017/6/16 17:18:53","STATE":"结案","RESULT":"合格","PICTUREID":[{"URL":"/Cms_Data/Contents/Fangshan/Folders/ANJIANFolder/FS_AJJABLOBFolder/~contents/F222JDRM7JXG5ESD/h.png","ID":"F222JDRM7JXG5ESD"},{"URL":"/Cms_Data/Contents/Fangshan/Folders/ANJIANFolder/FS_AJJABLOBFolder/~contents/49H2N6QGT5L9FHB7/1497850261955.jpg","ID":"49H2N6QGT5L9FHB7"}],"AUDIOID":[{"URL":"/Cms_Data/Contents/Fangshan/Folders/ANJIANFolder/FS_AJJABLOBFolder/~contents/TRBJ8Q9FUXTQT9NL/1497850278344.mp4","ID":"TRBJ8Q9FUXTQT9NL"}],"MEMO":"判定处置合格正常结案"}
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
         * USERID : CAF84AB36897B725E25758E0D5B2387CA93697EE
         * CASEID : ZZKJS64SV8NA82CZ
         * UPLOADTIME : 2017/6/16 17:18:53
         * STATE : 结案
         * RESULT : 合格
         * PICTUREID : [{"URL":"/Cms_Data/Contents/Fangshan/Folders/ANJIANFolder/FS_AJJABLOBFolder/~contents/F222JDRM7JXG5ESD/h.png","ID":"F222JDRM7JXG5ESD"},{"URL":"/Cms_Data/Contents/Fangshan/Folders/ANJIANFolder/FS_AJJABLOBFolder/~contents/49H2N6QGT5L9FHB7/1497850261955.jpg","ID":"49H2N6QGT5L9FHB7"}]
         * AUDIOID : [{"URL":"/Cms_Data/Contents/Fangshan/Folders/ANJIANFolder/FS_AJJABLOBFolder/~contents/TRBJ8Q9FUXTQT9NL/1497850278344.mp4","ID":"TRBJ8Q9FUXTQT9NL"}]
         * MEMO : 判定处置合格正常结案
         */

        private String USERID;
        private String CASEID;
        private String UPLOADTIME;
        private String STATE;
        private String RESULT;
        private String MEMO;
        private List<PICTUREIDBean> PICTUREID;
        private List<AUDIOIDBean> AUDIOID;

        public String getUSERID() {
            return USERID;
        }

        public void setUSERID(String USERID) {
            this.USERID = USERID;
        }

        public String getCASEID() {
            return CASEID;
        }

        public void setCASEID(String CASEID) {
            this.CASEID = CASEID;
        }

        public String getUPLOADTIME() {
            return UPLOADTIME;
        }

        public void setUPLOADTIME(String UPLOADTIME) {
            this.UPLOADTIME = UPLOADTIME;
        }

        public String getSTATE() {
            return STATE;
        }

        public void setSTATE(String STATE) {
            this.STATE = STATE;
        }

        public String getRESULT() {
            return RESULT;
        }

        public void setRESULT(String RESULT) {
            this.RESULT = RESULT;
        }

        public String getMEMO() {
            return MEMO;
        }

        public void setMEMO(String MEMO) {
            this.MEMO = MEMO;
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
             * URL : /Cms_Data/Contents/Fangshan/Folders/ANJIANFolder/FS_AJJABLOBFolder/~contents/F222JDRM7JXG5ESD/h.png
             * ID : F222JDRM7JXG5ESD
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

        public static class AUDIOIDBean {
            /**
             * URL : /Cms_Data/Contents/Fangshan/Folders/ANJIANFolder/FS_AJJABLOBFolder/~contents/TRBJ8Q9FUXTQT9NL/1497850278344.mp4
             * ID : TRBJ8Q9FUXTQT9NL
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
