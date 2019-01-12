package com.example.doldrum.tortoiseandrabbit.bean;

import com.example.doldrum.tortoiseandrabbit.app.Result;

public class UpdateBean extends Result {

    /**
     * data : {"versionCode":1,"versionName":"1.0.0","title":"为了用户体验更好 我们平台增加了许多新功能 已更新新版本  请您下载新版本 谢谢您的支持 官方网址www.guituyule.com","url":"http://fir.im/qlgk","ifupdate":"0"}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * versionCode : 1
         * versionName : 1.0.0
         * title : 为了用户体验更好 我们平台增加了许多新功能 已更新新版本  请您下载新版本 谢谢您的支持 官方网址www.guituyule.com
         * url : http://fir.im/qlgk
         * ifupdate : 0
         */

        private int versionCode;
        private String versionName;
        private String title;
        private String url;
        private String ifupdate;

        public int getVersionCode() {
            return versionCode;
        }

        public void setVersionCode(int versionCode) {
            this.versionCode = versionCode;
        }

        public String getVersionName() {
            return versionName;
        }

        public void setVersionName(String versionName) {
            this.versionName = versionName;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getIfupdate() {
            return ifupdate;
        }

        public void setIfupdate(String ifupdate) {
            this.ifupdate = ifupdate;
        }
    }
}
