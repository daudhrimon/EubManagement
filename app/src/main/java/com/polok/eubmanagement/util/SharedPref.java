package com.polok.eubmanagement.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.polok.eubmanagement.data.model.UserProfileData;

public class SharedPref {
    private static SharedPreferences mSharedPref;

    public static void init(Context context) {
        if (mSharedPref == null) mSharedPref = context.getSharedPreferences(
                "eub.management", Context.MODE_PRIVATE
        );
    }

    public static String read(String key, String defValue) {
        return mSharedPref.getString(key, defValue);
    }

    public static void write(String key, String value) {
        SharedPreferences.Editor prefsEditor = mSharedPref.edit();
        prefsEditor.putString(key, value);
        prefsEditor.apply();
    }

    public static void saveUserBatch(String batch) {
        write("user_batch", batch);
    }

    public static String getUserBatch() {
        return read("user_batch","");
    }

    public static void saveUserProfile(UserProfileData userProfileData) {
        write("user_profile",new Gson().toJson(userProfileData));
    }

    public static UserProfileData getUserProfile() {
        return new Gson().fromJson(read("user_profile",""), UserProfileData.class);
    }
}
