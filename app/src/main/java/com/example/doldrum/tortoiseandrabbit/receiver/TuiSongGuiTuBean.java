package com.example.doldrum.tortoiseandrabbit.receiver;

import java.util.List;

public class TuiSongGuiTuBean {


    private List<CaipiaojieguoBean> caipiaojieguo;

    public List<CaipiaojieguoBean> getCaipiaojieguo() {
        return caipiaojieguo;
    }

    public void setCaipiaojieguo(List<CaipiaojieguoBean> caipiaojieguo) {
        this.caipiaojieguo = caipiaojieguo;
    }

    public  class CaipiaojieguoBean {
        /**
         * id : 12580
         * shijian : 10-14
         * shijian_shi : 2018-10-14
         * qishu : 117
         * piao : 29220
         * haoma : 2,2,0
         */

        private int id;
        private String shijian;
        private String shijian_shi;
        private int qishu;
        private String piao;
        private String haoma;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getShijian() {
            return shijian;
        }

        public void setShijian(String shijian) {
            this.shijian = shijian;
        }

        public String getShijian_shi() {
            return shijian_shi;
        }

        public void setShijian_shi(String shijian_shi) {
            this.shijian_shi = shijian_shi;
        }

        public int getQishu() {
            return qishu;
        }

        public void setQishu(int qishu) {
            this.qishu = qishu;
        }

        public String getPiao() {
            return piao;
        }

        public void setPiao(String piao) {
            this.piao = piao;
        }

        public String getHaoma() {
            return haoma;
        }

        public void setHaoma(String haoma) {
            this.haoma = haoma;
        }
    }
}
