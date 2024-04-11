package com.zaar.meatkgb2_w.model.repository

import android.os.Bundle

interface SharedPreferencesRepository {

    /**
     *
     * @return bundle containing the boolean value
     */
    fun containsPreferences(keys: Array<String>): Bundle
    fun getPreferencesVal(keys: Array<String>): Bundle
    fun setSessionID(sessionID: String): Boolean
    fun setPreferences(incomingBundle: Bundle): Boolean
    fun deleteKeys(keys: Array<String>)
    fun clearStore()
}