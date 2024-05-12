package com.zaar2.meatKGB_w.model.repository

import android.os.Bundle

interface SharedPreferencesRepository {
    fun containsPreferences(keys: Array<String>): Bundle
    fun getPreferencesVal(keys: Array<String>): Bundle
//    fun setSessionID(sessionID: String): Boolean
    fun setPreferences(incomingBundle: Bundle): Boolean
    fun deleteKeys(keys: Array<String>)
    fun clearStore()
}