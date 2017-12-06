/*
 * Copyright (c) 2016. http://mobitech.io. All rights reserved.
 * Code is not permitted for commercial use w/o permission of Mobitech.io - support@mobitech.io .
 * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 */

package io.mobitech.contentdemo.app;

import android.app.Application;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

import com.google.android.gms.ads.identifier.AdvertisingIdClient;

import io.mobitech.contentdemo.util.SharedPreferencesUtil;

/**
 * Created on 25-May-16.
 */
public class ContentDemoApplication extends Application {

    private static final String TAG = ContentDemoApplication.class.getPackage() + "." + ContentDemoApplication.class.getSimpleName();

    @Override
    public void onCreate() {
        super.onCreate();

        //callback for user id:
        //Either takes the user's advertiserId or creates a unique ID, if
        new SetUserIdTask().execute();
    }


    private static class SetUserIdTask extends AsyncTask<Context, Void, Void> {

        @Override
        protected Void doInBackground(Context ... contexts) {
            if (contexts.length > 0) {

                Context context = contexts[0];

                String userAdvId = "";

                try {
                    AdvertisingIdClient.Info adInfo = AdvertisingIdClient.getAdvertisingIdInfo(context);
                    if (!adInfo.isLimitAdTrackingEnabled()) {
                        userAdvId = adInfo.getId();
                    }
                } catch (Exception e) {
                    Log.e(TAG, ((e.getMessage() == null) ? "" : e.getMessage()), e);
                }

                SharedPreferencesUtil sharedPreferencesUtil = new SharedPreferencesUtil(context);
                sharedPreferencesUtil.setUserId(userAdvId);

            } else {
                Log.e(TAG, "Failed to set user id: there are no context!");
            }

            return null;
        }
    }

}
