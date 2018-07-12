package com.konka.kkstar.bean;

import java.util.List;

/**
 * Created by HP on 2018-4-28.
 */

public class Actor {

    /**
     * localImageUrl : /storage/emulated/0/screenshot.jpg
     * resultList : [{"baike_image":"http://img0.imgtn.bdimg.com/it/u=3093487638,2925140367&fm=62","baikeUrl":"http://baike.baidu.com/item/%E9%99%88%E8%B5%AB/2493106","description":"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内...","detail":"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内地男演员、歌手、主持人。2009年，在都市爱情喜剧《爱情公寓》中凭借饰演曾小贤一角受到关注。2014年，加盟大型户外竞技真人秀节目《奔跑吧兄弟》，并主演电影版；同年，主演的都市爱情喜剧《爱情公寓4》在首播后打破国产电视剧网络播放纪录；12月17日，在安徽卫视\u201c2014国剧盛典\u201d中荣获年度极具青春号召力演员奖；12月24日，与Angelababy主演的爱情喜剧电影《微爱之渐入佳境》全国上映，票房突破2亿元。2015年，主演的古装探案喜剧《医馆笑传》和都市亲子剧《三个奶爸》播出；12月31日，参演的喜剧动作电影《唐人街探案》全国公映。2016年7月，陈赫出任《全民枪战》首席CCO。2017年主演电视剧《新世界》。","flag":1,"location":{"height":608,"left":902,"top":62,"width":601},"name":"陈赫","thumb":"http://b.hiphotos.baidu.com/xiaodu/pic/item/d4628535e5dde711090c92a8abefce1b9c166196.jpg"}]
     */

    private String localImageUrl;
    private List<ResultListBean> resultList;

    public String getLocalImageUrl() {
        return localImageUrl;
    }

    public void setLocalImageUrl(String localImageUrl) {
        this.localImageUrl = localImageUrl;
    }

    public List<ResultListBean> getResultList() {
        return resultList;
    }

    public void setResultList(List<ResultListBean> resultList) {
        this.resultList = resultList;
    }

    public static class ResultListBean {
        /**
         * baike_image : http://img0.imgtn.bdimg.com/it/u=3093487638,2925140367&fm=62
         * baikeUrl : http://baike.baidu.com/item/%E9%99%88%E8%B5%AB/2493106
         * description : 陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内...
         * detail : 陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内地男演员、歌手、主持人。2009年，在都市爱情喜剧《爱情公寓》中凭借饰演曾小贤一角受到关注。2014年，加盟大型户外竞技真人秀节目《奔跑吧兄弟》，并主演电影版；同年，主演的都市爱情喜剧《爱情公寓4》在首播后打破国产电视剧网络播放纪录；12月17日，在安徽卫视“2014国剧盛典”中荣获年度极具青春号召力演员奖；12月24日，与Angelababy主演的爱情喜剧电影《微爱之渐入佳境》全国上映，票房突破2亿元。2015年，主演的古装探案喜剧《医馆笑传》和都市亲子剧《三个奶爸》播出；12月31日，参演的喜剧动作电影《唐人街探案》全国公映。2016年7月，陈赫出任《全民枪战》首席CCO。2017年主演电视剧《新世界》。
         * flag : 1
         * location : {"height":608,"left":902,"top":62,"width":601}
         * name : 陈赫
         * thumb : http://b.hiphotos.baidu.com/xiaodu/pic/item/d4628535e5dde711090c92a8abefce1b9c166196.jpg
         */

        private String baike_image;
        private String baikeUrl;
        private String description;
        private String detail;
        private int flag;
        private LocationBean location;
        private String name;
        private String thumb;

        public String getBaike_image() {
            return baike_image;
        }

        public void setBaike_image(String baike_image) {
            this.baike_image = baike_image;
        }

        public String getBaikeUrl() {
            return baikeUrl;
        }

        public void setBaikeUrl(String baikeUrl) {
            this.baikeUrl = baikeUrl;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public String getDetail() {
            return detail;
        }

        public void setDetail(String detail) {
            this.detail = detail;
        }

        public int getFlag() {
            return flag;
        }

        public void setFlag(int flag) {
            this.flag = flag;
        }

        public LocationBean getLocation() {
            return location;
        }

        public void setLocation(LocationBean location) {
            this.location = location;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getThumb() {
            return thumb;
        }

        public void setThumb(String thumb) {
            this.thumb = thumb;
        }

        public static class LocationBean {
            /**
             * height : 608.0
             * left : 902.0
             * top : 62.0
             * width : 601.0
             */

            private double height;
            private double left;
            private double top;
            private double width;

            public LocationBean(double left, double top, double width, double height){
                this.left = left;
                this.top = top;
                this.width = width;
                this.height = height;
            }

            public double getHeight() {
                return height;
            }

            public void setHeight(double height) {
                this.height = height;
            }

            public double getLeft() {
                return left;
            }

            public void setLeft(double left) {
                this.left = left;
            }

            public double getTop() {
                return top;
            }

            public void setTop(double top) {
                this.top = top;
            }

            public double getWidth() {
                return width;
            }

            public void setWidth(double width) {
                this.width = width;
            }
        }
    }
}
