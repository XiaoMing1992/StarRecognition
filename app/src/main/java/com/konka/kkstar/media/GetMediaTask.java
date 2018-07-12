package com.konka.kkstar.media;

import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.konka.kkstar.bean.AlbumBean;
import com.konka.kkstar.bean.AlbumResultBean;
import com.konka.kkstar.bean.ResponseBean;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by Zhou Weilin on 2018-5-16.
 */

public class GetMediaTask {
    private final String TAG = "iqiyi_" + GetMediaTask.class.getSimpleName();
    private Context mContext;
    private GetInfoTask mGetInfoTask;
    private MediaResultListener mResultListener;


    public GetMediaTask(Context context, MediaResultListener listener){
        mContext = context;
        mResultListener = listener;
    }

    public void executeGetStarTask(String name) {
        cancelGetStarTask();
        mGetInfoTask = new GetInfoTask(name);
        mGetInfoTask.execute();
        Log.d(TAG,"executeGetStarTask-->name:"+name);

    }


    public void cancelGetStarTask() {
        if(null != mGetInfoTask && mGetInfoTask.getStatus() == AsyncTask.Status.RUNNING){
            mGetInfoTask.cancel(true);
        }
        Log.d(TAG,"cancelGetStarTask");
    }


    private class GetInfoTask extends AsyncTask<Void, Void, List<AlbumBean> > {
        private String mStarName;

        public GetInfoTask(String name){
            mStarName = name;
        }

        @Override
        protected List<AlbumBean>  doInBackground(Void... Void) {
            Log.d(TAG,"GetInfoTask-->doInBackground");
            List<AlbumBean>  albumBeanList = null;
            try{
                int page = 1;
                int pageSize = 30;
                List<Integer> supplierList = new ArrayList<>();
                supplierList.add(1);
                ResponseBean<AlbumResultBean> response = MediaApi.personSearch(page,pageSize,supplierList,mStarName);
                if(response!=null && response.getCode() == 200){
                    albumBeanList = response.getData().getAlbumList();
                }

            }catch (Exception e){
                e.printStackTrace();

            }
            return albumBeanList;
        }

        protected void onPostExecute(List<AlbumBean> result){
            Log.i(TAG,"kk-->GetInfoTask-->onPostExecute-->result:"+result);
            if(result !=null && result.size()>0){
                mResultListener.getMediaSucceed(result);
            }else {
                mResultListener.getMediaFailed();
            }

        }
    }



    public interface MediaResultListener{
        void getMediaSucceed(List<AlbumBean> albumBeanList);
        void getMediaFailed();
    }
}
