package com.konka.kkstar.bean;

import java.util.List;

/**
 * Created by Zhou Weilin on 2018-5-16.
 */

public class AlbumBean {

    /**
     * id : 2342716
     * name : 成龙回应“洗钱”质疑你相信我会贪那些钱吗
     * thridAlbumId : 124682800
     * bootParamType : 2
     * bootType : 2
     * bootParam : {"package":"com.gitvkonka.video","params":{"chnId":"7","videoId":"124682800","episodeId":"124682800","customer":"konka"}}
     * posterList : [{"spec":"480*270","url":"http://pic1.qiyipic.com/image/20140925/c7/d8/v_50346828_m_601_m2_480_270.jpg"},{"spec":"170*100","url":"http://pic1.qiyipic.com/image/20140925/c7/d8/v_50346828_m_601_m2_170_100.jpg"},{"spec":"145*90","url":"http://pic1.qiyipic.com/image/20140925/c7/d8/v_50346828_m_601_m2_145_90.jpg"},{"spec":"180*120","url":"http://pic1.qiyipic.com/image/20140925/c7/d8/v_50346828_m_601_m2_180_120.jpg"},{"spec":"480*360","url":"http://pic1.qiyipic.com/image/20140925/c7/d8/v_50346828_m_601_m2_480_360.jpg"},{"spec":"128*128","url":"http://pic1.qiyipic.com/image/20140925/c7/d8/v_50346828_m_601_m2_128_128.jpg"},{"spec":"195*260","url":"http://pic1.qiyipic.com/image/20140925/c7/d8/v_50346828_m_601_m2_195_260.jpg"}]
     */

    private int id;
    private String name;
    private String thridAlbumId;
    private int bootParamType;
    private int bootType;
    private String bootParam;
    private List<PosterListBean> posterList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getThridAlbumId() {
        return thridAlbumId;
    }

    public void setThridAlbumId(String thridAlbumId) {
        this.thridAlbumId = thridAlbumId;
    }

    public int getBootParamType() {
        return bootParamType;
    }

    public void setBootParamType(int bootParamType) {
        this.bootParamType = bootParamType;
    }

    public int getBootType() {
        return bootType;
    }

    public void setBootType(int bootType) {
        this.bootType = bootType;
    }

    public String getBootParam() {
        return bootParam;
    }

    public void setBootParam(String bootParam) {
        this.bootParam = bootParam;
    }

    public List<PosterListBean> getPosterList() {
        return posterList;
    }

    public void setPosterList(List<PosterListBean> posterList) {
        this.posterList = posterList;
    }

    public class PosterListBean {
        /**
         * spec : 480*270
         * url : http://pic1.qiyipic.com/image/20140925/c7/d8/v_50346828_m_601_m2_480_270.jpg
         */

        private String spec;
        private String url;

        public String getSpec() {
            return spec;
        }

        public void setSpec(String spec) {
            this.spec = spec;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        @Override
        public String toString() {
            return "PosterListBean{" +
                    "spec='" + spec + '\'' +
                    ", url='" + url + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "AlbumBean{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", thridAlbumId='" + thridAlbumId + '\'' +
                ", bootParamType=" + bootParamType +
                ", bootType=" + bootType +
                ", bootParam='" + bootParam + '\'' +
                ", posterList=" + posterList +
                '}';
    }
}
