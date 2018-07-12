package com.konka.kkstar.util;

import android.util.Log;


public class LogUtils {
    /**
     * 是否开启debug
     */
    public static boolean isDebug=true;

    public static void e(String TAG, String msg){
        if(isDebug){
            Log.e(TAG, msg+"");
        }
    }

    public static void i(String TAG, String msg){
        if(isDebug){
            Log.i(TAG, msg+"");
        }
    }

    public static void d(String TAG, String msg){
        if(isDebug){
            Log.d(TAG, msg+"");
        }
    }

    public static void w(String TAG, String msg){
        if(isDebug){
            Log.w(TAG, msg+"");
        }
    }

    public static void debugLongTexts(String tagName, String msg) {
        if (true) {
            int strLength = msg.length();
            int start = 0;
            int end = 2000;
            for (int i = 0; i < 100; i++) {
                if (strLength > end) {
                    Log.d(tagName + i, msg.substring(start, end));
                    start = end;
                    end = end + 2000;
                } else {
                    Log.d(tagName + i, msg.substring(start, strLength));
                    break;
                }
            }
        }
    }
}
