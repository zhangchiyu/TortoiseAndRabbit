package com.example.doldrum.tortoiseandrabbit.bean;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.example.doldrum.tortoiseandrabbit.app.Result;

import java.util.List;

public class GonggaolanBean extends Result {

    /**
     * msg : 存在相关信息
     * data : [{"id":215,"title":"公告2","add_time":"2018-11-22","fabuzhe":"","touxiang":"http://app.guituyule.com/images/touxiang1.jpeg","content":"公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2"},{"id":216,"title":"公告3","add_time":"2018-11-22","fabuzhe":"","touxiang":"http://app.guituyule.com/images/touxiang1.jpeg","content":"公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3公告3"},{"id":217,"title":"公告4","add_time":"2018-11-22","fabuzhe":"","touxiang":"http://app.guituyule.com/images/touxiang1.jpeg","content":"公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4公告4v"},{"id":214,"title":"【软件声明；本软件属于娱乐游戏 严禁赌博 线下交易出现纠纷与软件无关 】","add_time":"2018-10-25","fabuzhe":"","touxiang":"http://app.guituyule.com/images/touxiang1.jpeg","content":""}]
     */

    private String msg;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }


    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public  class DataBean  extends BaseObservable {
        /**
         * id : 215
         * title : 公告2
         * add_time : 2018-11-22
         * fabuzhe :
         * touxiang : http://app.guituyule.com/images/touxiang1.jpeg
         * content : 公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2公告2
         */

        private int id;
        private String title;
        private String add_time;
        private String fabuzhe;
        private String touxiang;
        private String content;


        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
        @Bindable
        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
        @Bindable
        public String getAdd_time() {
            return add_time;
        }

        public void setAdd_time(String add_time) {
            this.add_time = add_time;
        }
        @Bindable
        public String getFabuzhe() {
            return fabuzhe;
        }

        public void setFabuzhe(String fabuzhe) {
            this.fabuzhe = fabuzhe;
        }
        @Bindable
        public String getTouxiang() {
            return touxiang;
        }

        public void setTouxiang(String touxiang) {
            this.touxiang = touxiang;
        }
        @Bindable
        public String getContent() {
            return content;
        }

        public void setContent(String content) {
            this.content = content;
        }
    }
}
