package com.konka.kkstar;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Process;

/**
 * Created by HP on 2018-4-24.
 */

public class FaceReceiver extends BroadcastReceiver {

    String str = "{\"localImageUrl\":\"/storage/emulated/0/screenshot.jpg\",\"resultList\":[{\"baike_image\":\"http://img0.imgtn.bdimg.com/it/u\\u003d3093487638,2925140367\\u0026fm\\u003d62\",\"baikeUrl\":\"http://baike.baidu.com/item/%E9%99%88%E8%B5%AB/2493106\",\"description\":\"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内...\",\"detail\":\"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内地男演员、歌手、主持人。2009年，在都市爱情喜剧《爱情公寓》中凭借饰演曾小贤一角受到关注。2014年，加盟大型户外竞技真人秀节目《奔跑吧兄弟》，并主演电影版；同年，主演的都市爱情喜剧《爱情公寓4》在首播后打破国产电视剧网络播放纪录；12月17日，在安徽卫视“2014国剧盛典”中荣获年度极具青春号召力演员奖；12月24日，与Angelababy主演的爱情喜剧电影《微爱之渐入佳境》全国上映，票房突破2亿元。2015年，主演的古装探案喜剧《医馆笑传》和都市亲子剧《三个奶爸》播出；12月31日，参演的喜剧动作电影《唐人街探案》全国公映。2016年7月，陈赫出任《全民枪战》首席CCO。2017年主演电视剧《新世界》。\",\"flag\":1,\"location\":{\"height\":608.0,\"left\":902.0,\"top\":62.0,\"width\":601.0},\"name\":\"陈赫\",\"thumb\":\"http://b.hiphotos.baidu.com/xiaodu/pic/item/d4628535e5dde711090c92a8abefce1b9c166196.jpg\"}/*," +
            "{\"baike_image\":\"http://img0.imgtn.bdimg.com/it/u\\u003d3093487638,2925140367\\u0026fm\\u003d62\",\"baikeUrl\":\"http://baike.baidu.com/item/%E9%99%88%E8%B5%AB/2493106\",\"description\":\"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内...\",\"detail\":\"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内地男演员、歌手、主持人。2009年，在都市爱情喜剧《爱情公寓》中凭借饰演曾小贤一角受到关注。2014年，加盟大型户外竞技真人秀节目《奔跑吧兄弟》，并主演电影版；同年，主演的都市爱情喜剧《爱情公寓4》在首播后打破国产电视剧网络播放纪录；12月17日，在安徽卫视“2014国剧盛典”中荣获年度极具青春号召力演员奖；12月24日，与Angelababy主演的爱情喜剧电影《微爱之渐入佳境》全国上映，票房突破2亿元。2015年，主演的古装探案喜剧《医馆笑传》和都市亲子剧《三个奶爸》播出；12月31日，参演的喜剧动作电影《唐人街探案》全国公映。2016年7月，陈赫出任《全民枪战》首席CCO。2017年主演电视剧《新世界》。\",\"flag\":1,\"location\":{\"height\":608.0,\"left\":902.0,\"top\":62.0,\"width\":601.0},\"name\":\"陈赫\",\"thumb\":\"http://b.hiphotos.baidu.com/xiaodu/pic/item/d4628535e5dde711090c92a8abefce1b9c166196.jpg\"}*/]}";


    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals("android.kkface.demo.action")){
            //String info = intent.getStringExtra("info");
            System.out.println("FaceReceiver  ---> str = "+str);
            MyWindowManager.getInstance().createFaceMainView(context, str);
        }else if (action.equals("com.iflytek.xiri2.allActivity.QUERY")){
            Process.killProcess(Process.myPid());
        }else if (action.equals("konka.action.START_VOICE_SEARCH")){
            Process.killProcess(Process.myPid());
        }
    }
}
