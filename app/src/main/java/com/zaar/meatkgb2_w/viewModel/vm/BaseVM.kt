package com.zaar.meatkgb2_w.viewModel.vm

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.zaar.meatkgb2_w.data.LogPass
import com.zaar.meatkgb2_w.model.useCase.SessionIdUseCase
import kotlinx.coroutines.launch
import retrofit2.HttpException

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

    protected val mldIsInitExistsEnterprise = MutableLiveData<Boolean>()
    var ldIsInitExistsEnterprise: LiveData<Boolean> = mldIsInitExistsEnterprise
    //endregion

    fun obtainingValidSessionID(
        logPass: LogPass,
        isNewAccount: Boolean
    ) {
        viewModelScope.launch {
            Log.d("TAG", "MESSAGE: CurrentThread[${Thread.currentThread().name}]")
            val def = SessionIdUseCase(myContext)
                .obtainingValidSessionID(logPass, isNewAccount, this)
            val sessionId = def.await()
            Log.d("TAG", "MESSAGE: CurrentThread[${Thread.currentThread().name}]")
            if (sessionId.isNotEmpty())
                if (sessionId != "false") mldSessionID.value = sessionId
                else {
                    mldStageDescriptionForAppEntry.value = "!! registration of user account is failed !!"
                    mldStageDescriptionForAppEntry.value = "!! please check the correctness of the account data  !!"
                    mldIsInitExistsEnterprise.value = false
                }
            else {
                mldStageDescriptionForAppEntry.value = "!! user is not verified !!"
                mldIsInitExistsEnterprise.value = false
            }
        }
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