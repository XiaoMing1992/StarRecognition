package com.konka.kkstar.media;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Zhou Weilin on 2018-5-3.
 */

public class IqiyiCaller {
    private static final String TAG = "iqiyi_" + IqiyiCaller.class.getSimpleName();
    /**
     * 打开爱奇艺详情页
     *
     * @param context
     * @param videoId 专辑id
     * @param episodeId 视频id
     * @param chnId 频道id
     */
    public static void startIqiyiPlayer(Context context,String videoId,String episodeId,String chnId){
        Intent intent = new Intent("com.gitvkonka.video.action.ACTION_DETAIL");
        Bundle bundle = new Bundle();
        JSONObject playInfoJson = new JSONObject();
        try {
//            playInfoJson.put("playType","history");
//            playInfoJson.put("history","0");

            playInfoJson.put("videoId",videoId);
            playInfoJson.put("episodeId",episodeId);
            playInfoJson.put("chnId",chnId);
            playInfoJson.put("customer","konka");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        bundle.putString("playInfo",playInfoJson.toString());
        intent.putExtras(bundle);
        intent.setFlags(Intent.FLAG_INCLUDE_STOPPED_PACKAGES);
        context.sendBroadcast(intent);
        Log.d(TAG,"startIqiyiPlayer-->videoId:"+videoId+"-->episodeId:"+episodeId+"-->chnId:"+chnId);
    }
}
