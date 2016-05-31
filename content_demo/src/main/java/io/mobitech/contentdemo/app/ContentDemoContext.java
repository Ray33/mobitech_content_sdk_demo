/*
 * Copyright (c) 2016. http://mobitech.io. All rights reserved.
 * Code is not permitted for commercial use w/o permission of Mobitech.io - support@mobitech.io .
 * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package io.mobitech.contentdemo.app;

import android.app.Application;
import android.os.AsyncTask;
import android.provider.Settings;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import java.util.UUID;

import io.mobitech.content.ContentService;

/**
 * Created on 25-May-16.
 */
public class ContentDemoContext extends Application {
    private static final String TAG = ContentDemoContext.class.getPackage() + "." + ContentDemoContext.class.getSimpleName();

    public static String userID = "";

    @Override
    public void onCreate() {
        super.onCreate();


        //callback for user id
        AsyncTask<Void, Void, String> task = new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... params) {
                try {
                    AdvertisingIdClient.Info adInfo =  AdvertisingIdClient.getAdvertisingIdInfo(ContentDemoContext.this);
                    if (!adInfo.isLimitAdTrackingEnabled()){
                        return  adInfo.getId();
                    }else{
                        //generate
                        String android_id = Settings.Secure.getString(ContentDemoContext.this.getContentResolver(),
                                Settings.Secure.ANDROID_ID);
                        return android_id == null ?  new UUID(System.currentTimeMillis(), System.currentTimeMillis()*2).toString() : android_id;
                    }
                } catch (Exception e) {
                    Log.e(TAG, e.getMessage(), e);
                }
                return "";
            }

            @Override
            protected void onPostExecute(String advertId) {
                userID = advertId;
                ContentService.init(ContentDemoContext.this, userID);
            }

        };
        task.execute();
    }



}
