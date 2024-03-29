package com.polok.eubmanagement.util

import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson
import com.polok.eubmanagement.model.UserProfileData

object SharedPref {
    private var mSharedPref: SharedPreferences? = null

    fun init(context: Context?) {
        if (mSharedPref == null) mSharedPref = context?.getSharedPreferences(
            "eub.management", Context.MODE_PRIVATE
        )
    }

    fun read(key: String?, defValue: String?): String {
        return mSharedPref?.getString(key, defValue) ?: ""
    }

    fun write(key: String?, value: String?) {
        mSharedPref?.edit()?.apply { putString(key, value) }?.apply()
    }

    fun saveUserBatch(batch: String?) {
        write("user_batch_sec", batch)
    }

    fun getUserBatch(): String {
        return read("user_batch_sec", "")
    }

    fun saveUserProfile(userProfileData: UserProfileData?) {
        write("user_profile", Gson().toJson(userProfileData))
    }

    fun getUserProfile(): UserProfileData {
        return Gson().fromJson(
            read("user_profile", ""),
            UserProfileData::class.java
        )
    }
}