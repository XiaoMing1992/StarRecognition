package com.konka.kkstar;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.os.Process;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.konka.kkstar.bean.Actor;
import com.konka.kkstar.ui.CircleImageView;
import com.konka.kkstar.util.HttpHelper;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by HP on 2018-4-24.
 */

public class FaceMainView extends FrameLayout {

    private Context mContext;
    private WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
    private static WindowManager windowManager;
    private View view;

    private TextView actor_name;
    private TextView actor_description;
    private CircleImageView actor_pic;
    private LinearLayout linear_info_layout;
    private RelativeLayout actor_pic_layout;

    private int currentPosition = 0;

    private TextView left_right_tip;
    private TextView more_tip;
    private LinearLayout face_fail_layout;
    private LinearLayout info_layout;

    private ImageView faceImgView;
    private ImageView face_background;
    private final int DEFAULT_INCREMENT = 0;
    private Bitmap bitmap;
    private Canvas canvas;

    private Actor actor;
    private List<Actor.ResultListBean> actors;
    private Paint mPaint;
    private int mColor = Color.BLACK;
    private float mStrokeWidth = 1;

    private void loadAData(final String res) {
        String str = "{\"localImageUrl\":\"/storage/emulated/0/screenshot.jpg\",\"resultList\":[{\"baike_image\":\"http://img0.imgtn.bdimg.com/it/u\\u003d3093487638,2925140367\\u0026fm\\u003d62\",\"baikeUrl\":\"http://baike.baidu.com/item/%E9%99%88%E8%B5%AB/2493106\",\"description\":\"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内...\",\"detail\":\"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内地男演员、歌手、主持人。2009年，在都市爱情喜剧《爱情公寓》中凭借饰演曾小贤一角受到关注。2014年，加盟大型户外竞技真人秀节目《奔跑吧兄弟》，并主演电影版；同年，主演的都市爱情喜剧《爱情公寓4》在首播后打破国产电视剧网络播放纪录；12月17日，在安徽卫视“2014国剧盛典”中荣获年度极具青春号召力演员奖；12月24日，与Angelababy主演的爱情喜剧电影《微爱之渐入佳境》全国上映，票房突破2亿元。2015年，主演的古装探案喜剧《医馆笑传》和都市亲子剧《三个奶爸》播出；12月31日，参演的喜剧动作电影《唐人街探案》全国公映。2016年7月，陈赫出任《全民枪战》首席CCO。2017年主演电视剧《新世界》。\",\"flag\":1,\"location\":{\"height\":608.0,\"left\":902.0,\"top\":62.0,\"width\":601.0},\"name\":\"陈赫\",\"thumb\":\"http://b.hiphotos.baidu.com/xiaodu/pic/item/d4628535e5dde711090c92a8abefce1b9c166196.jpg\"}/*," +
                "{\"baike_image\":\"http://img0.imgtn.bdimg.com/it/u\\u003d3093487638,2925140367\\u0026fm\\u003d62\",\"baikeUrl\":\"http://baike.baidu.com/item/%E9%99%88%E8%B5%AB/2493106\",\"description\":\"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内...\",\"detail\":\"陈赫，1985年11月9日出生于福建省长乐市，毕业于上海戏剧学院表演系，上海话剧艺术中心演员，中国内地男演员、歌手、主持人。2009年，在都市爱情喜剧《爱情公寓》中凭借饰演曾小贤一角受到关注。2014年，加盟大型户外竞技真人秀节目《奔跑吧兄弟》，并主演电影版；同年，主演的都市爱情喜剧《爱情公寓4》在首播后打破国产电视剧网络播放纪录；12月17日，在安徽卫视“2014国剧盛典”中荣获年度极具青春号召力演员奖；12月24日，与Angelababy主演的爱情喜剧电影《微爱之渐入佳境》全国上映，票房突破2亿元。2015年，主演的古装探案喜剧《医馆笑传》和都市亲子剧《三个奶爸》播出；12月31日，参演的喜剧动作电影《唐人街探案》全国公映。2016年7月，陈赫出任《全民枪战》首席CCO。2017年主演电视剧《新世界》。\",\"flag\":1,\"location\":{\"height\":608.0,\"left\":902.0,\"top\":62.0,\"width\":601.0},\"name\":\"陈赫\",\"thumb\":\"http://b.hiphotos.baidu.com/xiaodu/pic/item/d4628535e5dde711090c92a8abefce1b9c166196.jpg\"}*/]}";
        //flag 标记该人物是否为所找人物，{1:是query所找人物，2:不是query 所找人物，-1:识别出人脸，但未识别出该人物是谁}

        //创建一个Gson对象
        Gson gson = new Gson();
        //Type typeOfT = new TypeToken<List<Actor>>() {}.getType();
        //进行解析
        //actors = gson.fromJson(str, typeOfT);

        actor = gson.fromJson(res, Actor.class);
        //actor = gson.fromJson(str, Actor.class);

        actors = actor.getResultList();

        removeInvalid(); //移除识别失败的明星的部分信息，如果明星百科图片为空、

/*        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 2;
        options.inPreferredConfig = Bitmap.Config.RGB_565;*/

        File file = new File(actor.getLocalImageUrl());
        if (file.length() <= 0) {
            System.out.println("file.length() = "+file.length());
            if (actors.size() > 0 && !actors.get(0).getThumb().isEmpty())
                    HttpHelper.downloadPicture(mContext, actors.get(0).getThumb(), R.drawable.img_default, R.drawable.img_default, face_background);
            else return;
            System.out.println("actors.get(0).getThumb() = "+actors.get(0).getThumb());
        } else {
            System.out.println("file.length() = "+file.length());
            try {
                FileInputStream inputStream = new FileInputStream(file);
                face_background.setImageBitmap(BitmapFactory.decodeFileDescriptor(inputStream.getFD()));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }
            //face_background.setImageBitmap(BitmapFactory.decodeFile(actor.getLocalImageUrl(), options));
        }


    }

    public FaceMainView(Context context, final String res) {
        super(context);
        this.mContext = context;
        windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        view = LayoutInflater.from(context).inflate(R.layout.face_main, this);
        initLayoutParams();

        initView();
        init();
        initData(res);
    }

    private int getScreenWidth() {
        return windowManager.getDefaultDisplay().getWidth();
    }

    private int getScreenHeight() {
        return windowManager.getDefaultDisplay().getHeight();
    }

    /**
     * 初始化参数
     */
    private void initLayoutParams() {
        //屏幕宽高
        int screenWidth = windowManager.getDefaultDisplay().getWidth();
        int screenHeight = windowManager.getDefaultDisplay().getHeight();
        System.out.println("--- screenWidth=" + screenWidth + " screenHeight=" + screenHeight);

        //总是出现在应用程序窗口之上。
        lp.type = WindowManager.LayoutParams.TYPE_PHONE;

        // FLAG_NOT_TOUCH_MODAL不阻塞事件传递到后面的窗口
        // FLAG_NOT_FOCUSABLE 悬浮窗口较小时，后面的应用图标由不可长按变为可长按,不设置这个flag的话，home页的划屏会有问题
//        lp.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
//                | WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;

        //lp.flags = WindowManager.LayoutParams.

        //悬浮窗默认显示的位置
        lp.gravity = Gravity.START | Gravity.BOTTOM;
        //悬浮窗的宽高
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = 489;
        lp.format = PixelFormat.TRANSPARENT;
        windowManager.addView(this, lp);
    }


    private void initView() {
        faceImgView = (ImageView) findViewById(R.id.faceImgView);
        face_background = (ImageView) findViewById(R.id.face_background);
        left_right_tip = (TextView) findViewById(R.id.left_right_tip);
        more_tip = (TextView) findViewById(R.id.more_tip);
        actor_name = (TextView) findViewById(R.id.actor_name);
        actor_description = (TextView) findViewById(R.id.description);
        actor_pic = (CircleImageView) findViewById(R.id.actor_pic);
        linear_info_layout = (LinearLayout) findViewById(R.id.linear_info_layout);
        actor_pic_layout = (RelativeLayout) findViewById(R.id.actor_pic_layout);
        face_fail_layout = (LinearLayout) findViewById(R.id.face_fail_layout);
        info_layout = (LinearLayout) findViewById(R.id.info_layout);
    }

    private void init() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setColor(mColor);
        mPaint.setStrokeWidth(mStrokeWidth);

        int view_w = (int) getResources().getDimension(R.dimen.face_background_width);
        int view_h = (int) getResources().getDimension(R.dimen.face_background_height);
        bitmap = Bitmap.createBitmap(view_w, view_h, Bitmap.Config.ARGB_4444);
        canvas = new Canvas(bitmap);
        faceImgView.draw(canvas);
    }

    private void initData(final String res) {
        loadAData(res);
        responseNameSelected();
        onItemSelect(currentPosition);
    }

    public void clear(Canvas canvas) {
        if (canvas == null) return;

        Paint paint = new Paint();
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
        canvas.drawPaint(paint);
        paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC));
        faceImgView.invalidate();
    }

    private void responseNameSelected() {
        if (actors != null && actors.size() > 0) {
            if (actors.size() > 1) {
                more_tip.setVisibility(View.VISIBLE);
                left_right_tip.setVisibility(View.VISIBLE);
                linear_info_layout.setBackgroundResource(R.drawable.many_card_boy);
            } else {
                more_tip.setVisibility(View.GONE);
                left_right_tip.setVisibility(View.GONE);
                linear_info_layout.setBackgroundResource(R.drawable.one_card_boy);
            }
            actor_description.setText(actors.get(0).getDescription());
            face_fail_layout.setVisibility(View.GONE);
            info_layout.setVisibility(View.VISIBLE);
        } else {
            left_right_tip.setVisibility(View.GONE);
            more_tip.setVisibility(View.GONE);
            info_layout.setVisibility(View.GONE);
            linear_info_layout.setBackgroundResource(R.drawable.one_card_boy);
            face_fail_layout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getKeyCode() == KeyEvent.KEYCODE_ENTER || event.getKeyCode() == KeyEvent.KEYCODE_DPAD_CENTER) {
                MyWindowManager.getInstance().createMovieFloatView(mContext, actor_name.getText().toString().trim());
                MyWindowManager.getInstance().hideFaceMainView();
                return true;
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_LEFT) {
                currentPosition--;
                if (actors != null && currentPosition < 0) {
                    currentPosition = (actors.size() + currentPosition) % actors.size();
                }
                onItemSelect(currentPosition);
                return true;
            } else if (event.getKeyCode() == KeyEvent.KEYCODE_DPAD_RIGHT) {
                currentPosition++;
                if (actors != null && currentPosition >= actors.size()) {
                    currentPosition = currentPosition % actors.size();
                }
                onItemSelect(currentPosition);
                return true;
            }
        }else if (event.getAction() == KeyEvent.ACTION_UP && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
                MyWindowManager.getInstance().removeFaceMainView(mContext);
                Process.killProcess(Process.myPid());
                return true;
        }
        return super.dispatchKeyEvent(event);
    }

    private void onItemSelect(int position) {
        if (position < 0 || actors == null || position >= actors.size()) return;
        actor_description.setText(actors.get(position).getDetail());
        actor_name.setText(actors.get(position).getName());
        if (!actors.get(position).getBaike_image().isEmpty())
            HttpHelper.downloadPicture(mContext, actors.get(position).getBaike_image(), R.drawable.img_default, R.drawable.img_default, actor_pic);
        changeOne(position);
    }

    private void removeInvalid() {
        if (actors == null) return;
        for (int i = 0; i < actors.size(); ) {
            if (/*actors.get(i).getBaike_image().isEmpty() || */actors.get(i).getFlag() != 1) {
                actors.remove(i);
                System.out.println("invalid actor is at position=" + i);
                System.out.println("the size of actors is " + actors.size());
            } else i++;
        }
    }

    private List<Actor.ResultListBean.LocationBean> computeLocation() {
        List<Actor.ResultListBean.LocationBean> newLocation = new ArrayList<>();
        int left = 0;
        int top = 0;
        int width = 0;
        int height = 0;

        int TV_W = getScreenWidth();
        int TV_H = getScreenHeight();
        int view_w = (int) getResources().getDimension(R.dimen.face_background_width);
        int view_h = (int) getResources().getDimension(R.dimen.face_background_height);

        System.out.println("--- TV_W=" + TV_W + " TV_H=" + TV_H + " view_w=" + view_w + " view_h=" + view_h);

        for (int i = 0; i < actors.size(); i++) {
            width = (view_w * (int) actors.get(i).getLocation().getWidth()) / TV_W;
            height = (view_h * (int) actors.get(i).getLocation().getHeight()) / TV_H;
            left = (view_w * (int) actors.get(i).getLocation().getLeft()) / TV_W;
            top = (view_h * (int) actors.get(i).getLocation().getTop()) / TV_H;
            System.out.println("--- left=" + left + " top=" + top + " width=" + width + " height=" + height);

            Actor.ResultListBean.LocationBean location = new Actor.ResultListBean.LocationBean(left, top, width, height);
            newLocation.add(location);
        }
        return newLocation;
    }

    private void changeOne(int position) {
        clear(canvas);
        List<Actor.ResultListBean.LocationBean> locations = computeLocation();
        int offset = 1;

        for (int i = 0; i < locations.size(); i++) {
            if (i == position) {
                mColor = Color.WHITE;
                mStrokeWidth = 4;
                mPaint = new Paint();
                mPaint.setAntiAlias(true);
                mPaint.setColor(mColor);
                mPaint.setStrokeWidth(mStrokeWidth);

                //左到右
                canvas.drawLine((float) locations.get(i).getLeft() - offset - DEFAULT_INCREMENT, (float) locations.get(i).getTop() - DEFAULT_INCREMENT,
                        (float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() / 5 + DEFAULT_INCREMENT, (float) locations.get(i).getTop() - DEFAULT_INCREMENT, mPaint);
                canvas.drawLine((float) locations.get(i).getLeft() + (4 * (float) locations.get(i).getWidth()) / 5 - DEFAULT_INCREMENT, (float) locations.get(i).getTop() - DEFAULT_INCREMENT,
                        (float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() + offset + DEFAULT_INCREMENT, (float) locations.get(i).getTop() - DEFAULT_INCREMENT, mPaint);

                //上到下
                canvas.drawLine((float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() + DEFAULT_INCREMENT, (float) locations.get(i).getTop() - offset - DEFAULT_INCREMENT,
                        (float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() + DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() / 5 + DEFAULT_INCREMENT, mPaint);
                canvas.drawLine((float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() + DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (4 * (float) locations.get(i).getHeight()) / 5 - DEFAULT_INCREMENT,
                        (float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() + DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() + offset + DEFAULT_INCREMENT, mPaint);

                //右到左
                canvas.drawLine((float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() + offset + DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() + DEFAULT_INCREMENT,
                        (float) locations.get(i).getLeft() + (4 * (float) locations.get(i).getWidth()) / 5 - DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() + DEFAULT_INCREMENT, mPaint);
                canvas.drawLine((float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() / 5 + DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() + DEFAULT_INCREMENT,
                        (float) locations.get(i).getLeft() - offset - DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() + DEFAULT_INCREMENT, mPaint);

                //下到上
                canvas.drawLine((float) locations.get(i).getLeft() - DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() + offset + DEFAULT_INCREMENT,
                        (float) locations.get(i).getLeft() - DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (4 * (float) locations.get(i).getHeight()) / 5 - DEFAULT_INCREMENT, mPaint);
                canvas.drawLine((float) locations.get(i).getLeft() - DEFAULT_INCREMENT, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() / 5 + DEFAULT_INCREMENT,
                        (float) locations.get(i).getLeft() - DEFAULT_INCREMENT, (float) locations.get(i).getTop() - offset - DEFAULT_INCREMENT, mPaint);

                continue;
            }
            mColor = Color.GRAY;
            mStrokeWidth = 2;
            mPaint.setColor(mColor);
            mPaint.setStrokeWidth(mStrokeWidth);

            //左到右
            canvas.drawLine((float) locations.get(i).getLeft() - offset, (float) locations.get(i).getTop(),
                    (float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() / 5, (float) locations.get(i).getTop(), mPaint);
            canvas.drawLine((float) locations.get(i).getLeft() + (4 * (float) locations.get(i).getWidth()) / 5, (float) locations.get(i).getTop(),
                    (float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() + offset, (float) locations.get(i).getTop(), mPaint);

            //上到下
            canvas.drawLine((float) locations.get(i).getLeft() + (float) locations.get(i).getWidth(), (float) locations.get(i).getTop(),
                    (float) locations.get(i).getLeft() + (float) locations.get(i).getWidth(), (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() / 5, mPaint);
            canvas.drawLine((float) locations.get(i).getLeft() + (float) locations.get(i).getWidth(), (float) locations.get(i).getTop() + (4 * (float) locations.get(i).getHeight()) / 5,
                    (float) locations.get(i).getLeft() + (float) locations.get(i).getWidth(), (float) locations.get(i).getTop() + (float) locations.get(i).getHeight(), mPaint);

            //右到左
            canvas.drawLine((float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() + offset, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight(),
                    (float) locations.get(i).getLeft() + (4 * (float) locations.get(i).getWidth()) / 5, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight(), mPaint);
            canvas.drawLine((float) locations.get(i).getLeft() + (float) locations.get(i).getWidth() / 5, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight(),
                    (float) locations.get(i).getLeft() - offset, (float) locations.get(i).getTop() + (float) locations.get(i).getHeight(), mPaint);

            //下到上
            canvas.drawLine((float) locations.get(i).getLeft(), (float) locations.get(i).getTop() + (float) locations.get(i).getHeight(),
                    (float) locations.get(i).getLeft(), (float) locations.get(i).getTop() + (4 * (float) locations.get(i).getHeight()) / 5, mPaint);
            canvas.drawLine((float) locations.get(i).getLeft(), (float) locations.get(i).getTop() + (float) locations.get(i).getHeight() / 5,
                    (float) locations.get(i).getLeft(), (float) locations.get(i).getTop(), mPaint);

        }
        faceImgView.setImageBitmap(bitmap);
    }
}
