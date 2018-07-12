package com.konka.kkstar.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.widget.ImageView;

import com.konka.kkstar.bean.AlbumBean;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by HP on 2018-4-20.
 */

public class HttpHelper {
    public static  Map<String, List<AlbumBean>> hasVisitedData = new HashMap<>();

    public static void downloadPicture(Context context, String url, int loadingRes, int errorRes, ImageView imageView) {
        Log.d("Picasso", "--- Picasso ---");
        if (url == null || url.isEmpty()) return;
        Picasso.with(context).load(url)
                .fit()
                .config(Bitmap.Config.RGB_565)
                .placeholder(loadingRes)
                .error(errorRes)
                .into(imageView);
    }
}
