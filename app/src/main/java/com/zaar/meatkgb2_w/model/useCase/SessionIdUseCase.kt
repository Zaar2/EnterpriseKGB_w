package com.zaar.meatkgb2_w.model.useCase

import android.content.Context
import android.util.Log
import com.zaar.meatkgb2_w.data.LogPass
import com.zaar.meatkgb2_w.model.repository.RemoteRepositoryImpl
import com.zaar.meatkgb2_w.model.repository.SharedPreferencesRepositoryImpl
import com.zaar.meatkgb2_w.utilities.types.TypeKeyForStore
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Date
import kotlin.coroutines.suspendCoroutine

class SessionIdUseCase(
    private val myContext: Context?
) {

    /**
     * 0.checking income value **isNewAccount**
     *
     * if true -> step2 (with **sessionID = ""**)
     *
     * if false -> step1
     *
     * 1.checking exists sessionID in the localData,
     *
     * if true -> step3 (**sessionID** = stored),
     *
     * if false -> step2 (**sessionID = ""**)
     *
     * 2.verification user and sessionID on server and receiving sessionId
     * -> returns response
     *
     * 3.verification stored sessionID on a server
     *
     * if response is "1" -> returns stored sessionId
     *
     * if response is not "1" -> step2
     *
     * @param logPass contains idEnterprise, userLogin and userPassword
     * @param isNewAccount **true** - this means that: after launch the app and inserting the registration data into the fields, user pressed button;
     * **false** - this means that: app is launch and the identification fragment launching is began
     */
    fun obtainingValidSessionID(
        logPass: LogPass,
        isNewAccount: Boolean,
        scope: CoroutineScope
    ): Deferred<String> {
        return scope.async(Dispatchers.IO) {
            Log.d("TAG", "MESSAGE: CurrentThread[${Thread.currentThread().name}]")
            //step 0
            if (isNewAccount) {
                //step 2
                return@async RemoteRepositoryImpl().identificationUser(logPass)
            } else {
                //step 1
                if (checkExistsSessionIdInStored()) {
                    //step 3
                    val sessionID = myContext?.let {
                        SharedPreferencesRepositoryImpl(it)
                            .getPreferencesVal(arrayOf(TypeKeyForStore.KEY_SESSION_ID.value))
                            .getString(TypeKeyForStore.KEY_SESSION_ID.value)
                    } ?: ""
                    if (RemoteRepositoryImpl().verificationSessionId(sessionID))
                        return@async sessionID
                    else return@async RemoteRepositoryImpl().identificationUser(logPass)
                } else {
                    //step 2
                    return@async RemoteRepositoryImpl().identificationUser(logPass)
                }
            }
        }
    }

    private fun checkExistsSessionIdInStored(): Boolean {
        val isExists = myContext?.let {
            SharedPreferencesRepositoryImpl(it).containsPreferences(
                arrayOf(TypeKeyForStore.KEY_SESSION_ID.value)
            )
        }
        return (isExists != null
                && isExists.getBoolean(TypeKeyForStore.KEY_SESSION_ID.value))
    }
}