package io.mobitech.contentdemo.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.util.UUID;

/**
 * Utility class that simplifies work with SharedPreferences
 * <p>
 * Created on 12/6/17.
 *
 * @author Sergey Pogoryelov
 */

public class SharedPreferencesUtil {
    private SharedPreferences sharedPreferences;

    private static final String USER_ID = "user_id";

    public SharedPreferencesUtil(Context context) {
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Gets the user id from the shared preferences
     * If there are no user id in the shared preferences returns random user id
     * @return unique user id
     */
    public String getUserId() {
        return sharedPreferences.getString(USER_ID, new UUID(System.currentTimeMillis(), System.currentTimeMillis() * 2).toString());
    }

    /**
     * Updates user id in shared preferences
     *
     * @param value new user id
     */
    public void setUserId(String value) {
        sharedPreferences.edit().putString(USER_ID, value).commit();    // used commit here in order to write changes immediately
    }
}
