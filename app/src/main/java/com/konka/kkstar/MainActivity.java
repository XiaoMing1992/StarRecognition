package com.konka.kkstar;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.konka.kkstar.bean.AlbumBean;
import com.konka.kkstar.media.GetMediaTask;

import java.util.List;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity {
    private GetMediaTask mGetMediaTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestAllPower();//申请权限
        mGetMediaTask = new GetMediaTask(this, mMediaResultListener);
        mGetMediaTask.executeGetStarTask("周杰伦");
    }


    private GetMediaTask.MediaResultListener mMediaResultListener = new GetMediaTask.MediaResultListener() {
        @Override
        public void getMediaSucceed(List<AlbumBean> albumBeanList) {
            Log.d("media_kkzwl", "getStarSucceed:" + albumBeanList.size());
        }

        @Override
        public void getMediaFailed() {
            Log.e("media_kkzwl", "getMediaFailed");
        }
    };

    public void requestAllPower() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PERMISSION_GRANTED) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
            } else {
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,
                                Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            for (int i = 0; i < permissions.length; i++) {
                if (grantResults[i] == PERMISSION_GRANTED) {
                    System.out.println("权限" + permissions[i] + "申请成功");
                    //Toast.makeText(this, "" + "权限" + permissions[i] + "申请成功", Toast.LENGTH_SHORT).show();
                } else {
                    System.out.println("权限" + permissions[i] + "申请失败");
                    //Toast.makeText(this, "" + "权限" + permissions[i] + "申请失败", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
}
