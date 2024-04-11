package com.zaar.meatkgb2_w.model.repository

import android.content.Context
import android.os.Bundle
import com.zaar.meatkgb2_w.model.local.EnabledAppUser
import com.zaar.meatkgb2_w.model.local.api_preferences.MyPreferences
import com.zaar.meatkgb2_w.utilities.types.TypeKeyForStore

class SharedPreferencesRepositoryImpl(
    context: Context,
): SharedPreferencesRepository {
    private val myPreferences: MyPreferences = MyPreferences(context)

    override fun containsPreferences(keys: Array<String>): Bundle = myPreferences.contains(keys)
    override fun getPreferencesVal(keys: Array<String>): Bundle =
        myPreferences.getPreferencesVal(keys)

    override fun setSessionID(sessionID: String): Boolean {
        EnabledAppUser.instance?.sessionID = sessionID
        val bundle = Bundle()
        bundle.putString(TypeKeyForStore.KEY_SESSION_ID.value, sessionID)
        return setPreferences(bundle)
    }

    /**
     * @param incomingBundle Strings(key->nameParam, value->valueParam)
     * @return if true -> list of inserted value; if false -> "false"
     */
    override fun setPreferences(incomingBundle: Bundle): Boolean =
        myPreferences.setPreferences(incomingBundle)

    override fun deleteKeys(keys: Array<String>) {
        myPreferences.deleteKeys(keys)
    }

    override fun clearStore() {
        myPreferences.clear()
    }
}