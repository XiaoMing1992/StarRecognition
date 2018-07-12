package com.konka.kkstar;

import android.content.Context;
import android.view.View;
import android.view.WindowManager;

/**
 * Created by HP on 2018-4-24.
 */

public class MyWindowManager {

    private FaceMainView faceMainView;
    private MovieFloatView movieFloatView;
    private static volatile MyWindowManager instance;

    private MyWindowManager() {
    }

    public static MyWindowManager getInstance() {
        if (instance == null) {
            synchronized (MyWindowManager.class) {
                if (instance == null)
                    instance = new MyWindowManager();
            }
        }
        return instance;
    }

    /**
     * 创建悬浮窗
     */
    public void createFaceMainView(Context context, String res) {
        if (faceMainView == null)
            faceMainView = new FaceMainView(context, res);
    }

    /**
     * 移除悬浮窗
     *
     * @param context
     */
    public void removeFaceMainView(Context context) {
        if (faceMainView != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.removeView(faceMainView);
            faceMainView = null;
        }
    }

    /**
     * 隐藏悬浮窗
     *
     * @param
     */
    public void hideFaceMainView() {
        if (faceMainView != null && faceMainView.getVisibility() == View.VISIBLE) {
            faceMainView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示悬浮窗
     *
     * @param
     */
    public void showFaceMainView() {
        if (faceMainView != null && faceMainView.getVisibility() == View.GONE) {
            faceMainView.setVisibility(View.VISIBLE);
        }
    }

    /**
     * 创建悬浮窗
     */
    public void createMovieFloatView(Context context) {
        if (movieFloatView == null)
            movieFloatView = new MovieFloatView(context);
    }

    /**
     * 创建悬浮窗
     */
    public void createMovieFloatView(Context context, final String name) {
        if (movieFloatView == null)
            movieFloatView = new MovieFloatView(context, name);
    }

    /**
     * 移除悬浮窗
     *
     * @param context
     */
    public void removeMovieFloatView(Context context) {
        if (movieFloatView != null) {
            WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            windowManager.removeView(movieFloatView);
            movieFloatView = null;
        }
    }

    /**
     * 隐藏悬浮窗
     *
     * @param
     */
    public void hideMovieFloatView() {
        if (movieFloatView != null && movieFloatView.getVisibility() == View.VISIBLE) {
            movieFloatView.setVisibility(View.GONE);
        }
    }

    /**
     * 显示悬浮窗
     *
     * @param
     */
    public void showMovieFloatView() {
        if (movieFloatView != null && movieFloatView.getVisibility() == View.GONE) {
            movieFloatView.setVisibility(View.VISIBLE);
        }
    }
}
