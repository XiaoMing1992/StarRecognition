package com.konka.kkstar;

import android.util.Log;

import com.konka.athenasdk.AthenaSDK;
import com.konka.athenasdk.IVoicesListener;
import com.konka.athenasdk.serve.AthenaAppService;

/**
 * Created by DongJunJie on 2018-1-26.
 */

public class MyService extends AthenaAppService {
    @Override
    public AthenaSDK getAthenaSDK() {
        AthenaSDK sdk = new AthenaSDK(this);
        sdk.setListener(new IVoicesListener() {
            @Override
            public void onVoicesCall() {

            }

            @Override
            public boolean onFeedBack(int i, String s, String s1, String s2, String s3, String s4) {
                Log.d("kkstar", i + " " + s + " " + s1 + " " + s2 + " " + s3 + " " + s4);
                MyWindowManager.getInstance().createFaceMainView(getApplicationContext(), s3);
                return false;
            }
        });
        return sdk;
    }
}
