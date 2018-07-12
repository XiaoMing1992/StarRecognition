package com.konka.kkstar.media;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.konka.Signature;
import com.konka.kkstar.bean.AlbumResultBean;
import com.konka.kkstar.bean.ResponseBean;
import com.konka.kkstar.util.CommUtils;
import com.konka.kkstar.util.HttpKit;

import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Zhou Weilin on 2018-5-16.
 */

public class MediaApi {
    private static final String TAG = "media_" + MediaApi.class.getSimpleName();
    private static final String URL_BASE_OFFICIAL = "http://test.kkapp.com/kkmediadbserver";//正式环境接口域名
    private static final String URL_BASE_TEST = "http://test.kkapp.com/kkmediadbserver";//测试环境接口域名
    private static final String URL_BASE_CURRENT = URL_BASE_TEST;//当前正在使用的域名
    private static final String APP_KEY = "3HsSjuXINECjwLJy";
    private static final String APP_SECRET = "3o6HFDiuWuXbeqF67qmAvJH1ejYcso7p";
    private static final String SALT = APP_SECRET;//md5加签的盐值
    private static final String CODE_FORMAT = "UTF-8";//md5加签的盐值


    /**
     * 根据人名搜索媒资结果
     * @param page 页数
     * @param pageSize 每页返回总数
     * @param suppliers 供应商id列表
     * @param name 人物名称
     * @return
     */
    public static ResponseBean<AlbumResultBean> personSearch(int page, int pageSize, List<Integer> suppliers, String name){
        ResponseBean<AlbumResultBean> albumResponseBean = null;
        try{
            Map<String,String> heads = new HashMap<String, String>();
            heads.put("Authorization",APP_KEY);

            Map<String,String> params = new HashMap<String, String>();
            params.put("supplierIdList", suppliers.toString());//加签
            params.put("personName", name);//加签
            params.put("appKey", APP_KEY);//加签

            String sign = Signature.sign(params, SALT, Charset.forName(CODE_FORMAT));
            params.put("sign",sign);
            params.remove("appKey");
            params.put("personName", URLEncoder.encode(name,CODE_FORMAT));//中文url加密，不然中文请求会乱码
            params.put("supplierIdList", CommUtils.join(suppliers,","));
            params.put("pagination.page",String.valueOf(page));
            params.put("pagination.pageSize",String.valueOf(pageSize));


            String url = URL_BASE_CURRENT + "/v1/albums/person-search";
            Log.d(TAG,"personSearch-->heads:"+heads+"  params:"+params);
            Log.d(TAG,"personSearch-->url:"+url);
            String result = HttpKit.sendGet(url,params);
            Log.d(TAG,"personSearch-->result:"+result);
            if(!TextUtils.isEmpty(result)){
                Type idType = new TypeToken<ResponseBean<AlbumResultBean>>(){}.getType();
                albumResponseBean = new Gson().fromJson(result,idType);
            }

        }catch (Exception e){
            e.printStackTrace();
        }
        Log.d(TAG,"personSearch-->albumResponseBean:"+albumResponseBean);
        return albumResponseBean;
    }




}
