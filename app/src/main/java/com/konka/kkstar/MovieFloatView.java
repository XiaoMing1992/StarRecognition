package com.konka.kkstar;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.Process;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.konka.kkstar.adapter.MovieAdapter;
import com.konka.kkstar.bean.AlbumBean;
import com.konka.kkstar.media.IqiyiCaller;
import com.konka.kkstar.media.GetMediaTask;
import com.konka.kkstar.ui.AppRecyclerView;
import com.konka.kkstar.ui.RecycleViewDivider;
import com.konka.kkstar.util.HttpHelper;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2018-4-25.
 */

public class MovieFloatView extends FrameLayout implements MovieAdapter.OnItemSelectedListener, MovieAdapter.OnItemClickListener {
    private Context mContext;

    private AppRecyclerView movie_recyclerview;
    private GridLayoutManager mLayoutMgr;

    private LinearLayout content_layout;
    private LinearLayout movie_loading_layout;
    private RelativeLayout movie_empty_layout;
    private RelativeLayout movie_layout;
    private MovieAdapter movieAdapter;
    private List<AlbumBean> movies;

    private TextView num_tv;
    private ImageView back;

    private int total_page = 0;
    private int current_page = 0;
    private int current_position = 0;

    private final int ITEM_SPAN_COUNT = 6;
    private int row_child_num = 6; //每一行多少item

    private WindowManager.LayoutParams lp;
    private static WindowManager windowManager;
    private View view;

    private long mCurrentFlyTime;
    private long mLastFlyTime;
    private final int Y_OFFSET = 80;
    private final int KEYCODE_DPAD_DOWN = 1;

    private GetMediaTask mMediaTask;
    private String currentName = "";
    private static final int SUCESS = 0x00;
    private static final int FAIL = 0x01;

    private Handler mHandler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case SUCESS:
                    System.out.println("SUCESS-->" + (new Date()));
                    movie_loading_layout.setVisibility(GONE);
                    back.setVisibility(VISIBLE);
                    content_layout.setVisibility(VISIBLE);

                    movieAdapter.notifyDataSetChanged(); //刷新
                    current_page = 1;
                    isEmpty();
                    updatePageTip();
                    movie_recyclerview.requestFocus();
                    break;

                case FAIL:
                    isEmpty();
                    movie_loading_layout.setVisibility(GONE);
                    break;
            }
        }
    };

    public MovieFloatView(Context context) {
        super(context);
        this.mContext = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        view = LayoutInflater.from(context).inflate(R.layout.float_movie, this);
        lp = new WindowManager.LayoutParams();
        initLayoutParams();

        initView();
        initData();
    }

    public MovieFloatView(Context context, final String name) {
        super(context);
        this.mContext = context;
        this.currentName = name;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        view = LayoutInflater.from(context).inflate(R.layout.float_movie, this);
        lp = new WindowManager.LayoutParams();
        initLayoutParams();

        initView();
        initData();
    }

    /**
     * 初始化参数
     */
    private void initLayoutParams() {
        //屏幕宽高
//        int screenWidth = windowManager.getDefaultDisplay().getWidth();
//        int screenHeight = windowManager.getDefaultDisplay().getHeight();

        //总是出现在应用程序窗口之上。
        lp.type = WindowManager.LayoutParams.TYPE_PHONE;

        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按,不设置这个flag的话，home页的划屏会有问题
//        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        //lp.flags = WindowManager.LayoutParams.

        //悬浮窗默认显示的位置
        lp.gravity = Gravity.START | Gravity.BOTTOM;
        //指定位置
/*        lp.x = screenWidth - view.getLayoutParams().width * 2;
        lp.y = screenHeight / 2 + view.getLayoutParams().height * 2;*/
        //悬浮窗的宽高
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 489;
        lp.format = PixelFormat.TRANSPARENT;
        windowManager.addView(this, lp);
    }


    private void initView() {
        movie_recyclerview = (AppRecyclerView) findViewById(R.id.movie_recyclerview);
        num_tv = (TextView) findViewById(R.id.num_tv);

        back = (ImageView) findViewById(R.id.back);
        content_layout = (LinearLayout) findViewById(R.id.content_layout);

        movie_loading_layout = (LinearLayout) findViewById(R.id.movie_loading_layout);
        movie_empty_layout = (RelativeLayout) findViewById(R.id.movie_empty_layout);
        movie_layout = (RelativeLayout) findViewById(R.id.movie_layout);
        back.setOnFocusChangeListener(new OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    System.out.println("--- back get focus");
                }
            }
        });
        back.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                System.out.println("--- back is clicked");
                MyWindowManager.getInstance().showFaceMainView();
                MyWindowManager.getInstance().removeMovieFloatView(mContext);
            }
        });
    }

    private GetMediaTask.MediaResultListener mIqiyiResultListener = new GetMediaTask.MediaResultListener() {
        @Override
        public void getMediaSucceed(List<AlbumBean> albumBeanList) {
            if (albumBeanList == null) {
                System.out.println("--->fail");
                mHandler.sendEmptyMessage(FAIL);
                return;
            }
            Log.d("kkzwl", "getStarSucceed:" + albumBeanList.size());

            for (int i = 0; i < albumBeanList.size(); i++) {
                movies.add(albumBeanList.get(i));
            }
            if (!currentName.isEmpty())
                HttpHelper.hasVisitedData.put(currentName, movies);

            System.out.println("--->starBean.size()" + albumBeanList.size());
            System.out.println("--->movies.size()" + movies.size());
            mHandler.sendEmptyMessage(SUCESS);
        }

        @Override
        public void getMediaFailed() {
            mHandler.sendEmptyMessage(FAIL);
        }

    };

    private void initData() {
        movies = new ArrayList<>();
        mMediaTask = new GetMediaTask(this.mContext, mIqiyiResultListener);
        movieAdapter = new MovieAdapter(mContext, movies);

        mLayoutMgr = new GridLayoutManager(mContext, ITEM_SPAN_COUNT, LinearLayoutManager.VERTICAL, false);
        movie_recyclerview.setLayoutManager(mLayoutMgr);
        movie_recyclerview.addItemDecoration(new RecycleViewDivider(mContext, true,
                (int) this.getResources().getDimension(R.dimen.movie_item_dividerHeight),
                (int) this.getResources().getDimension(R.dimen.movie_item_dividerWidth), row_child_num));

        movie_recyclerview.setAdapter(movieAdapter);

        movieAdapter.setItemSelectedListener(this);
        movieAdapter.setOnItemClickListener(this);

        //movie_recyclerview.requestFocus();
        back.setVisibility(GONE);
        content_layout.setVisibility(GONE);
        movie_loading_layout.setVisibility(VISIBLE);

        //currentName = "胡歌";
        System.out.println("currentName = " + currentName);
        if (HttpHelper.hasVisitedData.get(currentName) == null || HttpHelper.hasVisitedData.get(currentName).isEmpty())
            mMediaTask.executeGetStarTask(currentName);
        else {
            for (int i = 0; i < HttpHelper.hasVisitedData.get(currentName).size(); i++) {
                movies.add(HttpHelper.hasVisitedData.get(currentName).get(i));
            }
            mHandler.sendEmptyMessage(SUCESS);
            System.out.println("--- movie from memory cache ---");
        }
        updatePageTip();
    }

    private void updatePageTip() {
        total_page = (movies.size() % row_child_num > 0) ? (movies.size() / row_child_num + 1) : (movies.size() / row_child_num);
        num_tv.setText("" + current_page + "/" + total_page);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_DOWN) {
                System.out.println("right --> position=" + current_position + " current_page=" + current_page + " total_page=" + total_page);
                if (current_page == total_page) {
                    tremble(movie_recyclerview, KEYCODE_DPAD_DOWN);
                    return true;
                }
            } else if ((event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER)
                    && movies != null && movies.size() <= 0) {
                MyWindowManager.getInstance().showFaceMainView();
                MyWindowManager.getInstance().removeMovieFloatView(mContext);
                return true;
            }
        }else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            //MyWindowManager.getInstance().showFaceMainView();
            if (mMediaTask != null) {
                mMediaTask.cancelGetStarTask();
            }
            MyWindowManager.getInstance().removeFaceMainView(mContext);
            MyWindowManager.getInstance().removeMovieFloatView(mContext);
            Process.killProcess(Process.myPid());
            return true;
        }
        return super.dispatchKeyEvent(event);
    }

    @Override
    public void OnItemSelected(int position) {
        current_position = position;
        System.out.println("OnItemSelected position=" + current_position);
        current_page = current_position / row_child_num + 1;
        num_tv.setText("" + current_page + "/" + total_page);
        movie_recyclerview.scrollToPosition(current_position);
    }

    /**
     * 抖动
     *
     * @param view
     * @param type
     */
    private void tremble(View view, int type) {
        mCurrentFlyTime = System.currentTimeMillis();
        if (mCurrentFlyTime - mLastFlyTime <= 200) {
            return;
        }
        mLastFlyTime = mCurrentFlyTime;

        AnimatorSet tAnimatorSet = new AnimatorSet();
        ObjectAnimator tAnimator1 = ObjectAnimator.ofFloat(view, "translationY", type == KEYCODE_DPAD_DOWN ? Y_OFFSET : -Y_OFFSET);
        tAnimator1.setDuration(100);
        ObjectAnimator tAnimator2 = ObjectAnimator.ofFloat(view, "translationY", 0);
        tAnimator2.setDuration(100);
        tAnimatorSet.play(tAnimator1).before(tAnimator2);
        tAnimatorSet.setInterpolator(new DecelerateInterpolator());
        tAnimatorSet.start();
    }

    @Override
    public void OnItemClick(int position) {
        if (position < 0 || (movies != null && position >= movies.size())) return;
        String bootParm = movies.get(position).getBootParam();
        try {
            JSONObject bootJson = new JSONObject(bootParm);
            String videoId = String.valueOf(bootJson.getJSONObject("params").getString("videoId"));
            String episodeId = String.valueOf(bootJson.getJSONObject("params").getString("episodeId"));
            String chnId = String.valueOf(bootJson.getJSONObject("params").getString("chnId"));
            System.out.println("--->OnItemClick, videoId=" + videoId + ", episodeId=" + episodeId + ", chnId=" + chnId);
            IqiyiCaller.startIqiyiPlayer(mContext, videoId, episodeId, chnId);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        Process.killProcess(Process.myPid());
    }

    private void isEmpty() {
        if (movies != null) {
            if (movies.size() == 0) {
                movie_layout.setVisibility(GONE);
                movie_empty_layout.setVisibility(VISIBLE);
            } else {
                movie_layout.setVisibility(VISIBLE);
                movie_empty_layout.setVisibility(GONE);
            }
        } else {
            movie_layout.setVisibility(GONE);
            movie_empty_layout.setVisibility(VISIBLE);
        }
    }
}
