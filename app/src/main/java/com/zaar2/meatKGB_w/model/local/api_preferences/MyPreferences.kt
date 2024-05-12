package com.zaar2.meatKGB_w.model.local.api_preferences

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.zaar2.meatKGB_w.utilities.types.AppConstStr
import java.io.IOException
import java.security.GeneralSecurityException

class MyPreferences(
    context: Context,
) {
    private lateinit var preferences: SharedPreferences
    private lateinit var masterKeyAlias: MasterKey
    init {
//        preferences = context.getSharedPreferences(
//            AppConstStr.APP_PREFERENCES_NAME.value, Context.MODE_PRIVATE
//        )
        try {
            masterKeyAlias = MasterKey.Builder(context)
                .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
                .build()
            preferences = EncryptedSharedPreferences.create(
                context.applicationContext,
                AppConstStr.APP_PREFERENCES_NAME.value,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } catch (exception: GeneralSecurityException) {
            Log.e("to cryptoPreferences:", exception.toString())
        } catch (exception: IOException) {
            Log.e("to cryptoPreferences:", exception.toString())
        }
    }

    /**
     * @param incomingBundle Strings(key->nameParam, value->valueParam)
     * @return if true -> list of inserted value; if false -> "false"
     */
    fun setPreferences(incomingBundle: Bundle): Boolean {
        val builder = StringBuilder()
        val editor = preferences.edit()
        val keys = incomingBundle.keySet()
        for (key in keys) {
            editor.putString(key, incomingBundle.getString(key, "nonVal"))
            builder
                .append(key).append(" : ").append(incomingBundle.getString(key, "nonVal"))
                .append("\n")
        }
        return editor.commit()
    }

    /**
     * @return bundle containing the boolean value
     */
    fun contains(keys: Array<String>): Bundle {
        Log.d("TAG", "MESSAGE: CurrentThread[${Thread.currentThread().name}]")
        val result = Bundle()
        for (key in keys) result.putBoolean(key, preferences.contains(key))
        return result
    }

    fun getPreferencesVal(keys: Array<String>): Bundle {
        Log.d("TAG", "MESSAGE: CurrentThread[${Thread.currentThread().name}]")
        val result = Bundle()
        for (key in keys) result.putString(key, preferences.getString(key, ""))
        return result
    }

    fun deleteKeys(keys: Array<String>) {
        val editor = preferences.edit()
        for (key in keys) {
            editor.remove(key).apply()
        }
    }

    fun clear() {
        val editor = preferences.edit()
        editor.clear()
        editor.apply()
    }
}