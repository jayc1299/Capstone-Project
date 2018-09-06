package com.nanodegree.newyorktravel.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class UserUtils {

    private static final String SHARED_PREFS_USER_ID = "shared_prefs_user_id";

    public void setUserId(Context context, String userId){
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences(context).edit();
        editor.putString(SHARED_PREFS_USER_ID, userId);
        editor.apply();
    }

    public String getUserId(Context context){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context);
        return sharedPreferences.getString(SHARED_PREFS_USER_ID, "");
    }

}