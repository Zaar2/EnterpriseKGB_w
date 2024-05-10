package com.zaar.meatKGB_w.viewModel.vm

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaar.meatKGB_w.data.LogPass
import com.zaar.meatKGB_w.model.useCase.SessionIdUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlin.coroutines.ContinuationInterceptor

open class BaseVM(
    open val myContext: Context,
): ViewModel() {
    //region-------------------RX LIVEDATA VARIABLES----------------------
    protected var mldStageDescriptionForAppEntry = MutableLiveData<String>()
    fun ldStageDescriptionForAppEntry(): LiveData<String> = mldStageDescriptionForAppEntry

    //endregion

    //region-------------------USER LIVEDATA VARIABLES----------------------
    protected var mldUserData = MutableLiveData<LogPass>()
    fun ldUserData(): LiveData<LogPass> = mldUserData

    //endregion

    //region-------------------APP LIVEDATA VARIABLES----------------------
    private var mldSessionID = MutableLiveData<String>()
    fun ldSessionID(): LiveData<String> = mldSessionID

    private var mldIsOnline = MutableLiveData<String?>()
    fun ldIsOnline(): LiveData<String?> = mldIsOnline

    protected val mldIsInitExistsEnterprise = MutableLiveData<Boolean>()
    var ldIsInitExistsEnterprise: LiveData<Boolean> = mldIsInitExistsEnterprise
    //endregion

    /**
     * @param logPass object that contains primary data for identification of user account (enterpriseId, login, password)
     */
    fun obtainingValidSessionID(
        logPass: LogPass,
        isNewAccount: Boolean
    ) {
        viewModelScope.launch {
            Log.d(
                "TAG",
                "Dispatcher = ${coroutineContext[ContinuationInterceptor]},\n MESSAGE: CurrentThread[${Thread.currentThread().name}]"
            )
            if (isOnline()) {
                val sessionId = SessionIdUseCase(myContext)
                    .obtainingValidSessionID(logPass, isNewAccount)
                    .await()
                if (sessionId.isNotEmpty()) {
                    if (sessionId != "false") mldSessionID.value = sessionId
                    else {
                        mldStageDescriptionForAppEntry.value =
                            "!! registration of user account is failed !!"
                        mldStageDescriptionForAppEntry.value =
                            "!! please check the correctness of the account data  !!"
                        mldIsInitExistsEnterprise.value = false
                    }
                } else {
                    mldStageDescriptionForAppEntry.value = "!! user is not verified !!"
                    mldIsInitExistsEnterprise.value = false
                }
            }
        }
    }

    fun isOnline(): Boolean {
        val connectivityManager =
            myContext.getSystemService((Context.CONNECTIVITY_SERVICE)) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    mldIsOnline.postValue("TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_BLUETOOTH")
                    mldIsOnline.postValue("TRANSPORT_BLUETOOTH")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    mldIsOnline.postValue("TRANSPORT_ETHERNET")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    mldIsOnline.postValue("TRANSPORT_WIFI")
                    return true
                }
            }
        }
        mldIsOnline.postValue(null)
        return false
    }

    /**
     * only if **Throwable** extends **HttpException**
     */
    protected fun getDescriptionLineFromThrowable(e: Throwable): String {
        return if (e is HttpException)
            """HTTP code is ${e.code()}"""
        else ""
    }
}