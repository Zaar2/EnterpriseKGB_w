package com.zaar.meatKGB_w.viewModel.vm

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zaar.meatKGB_w.data.LogPass
import com.zaar.meatKGB_w.model.repository.LocalDBRepositoryImpl
import com.zaar.meatKGB_w.model.repository.SharedPreferencesRepositoryImpl
import com.zaar.meatKGB_w.model.useCase.UpdateProductUseCase
import com.zaar.meatKGB_w.model.useCase.UpdateShopUseCase
import com.zaar.meatKGB_w.model.useCase.UpdateWorkerUseCase
import com.zaar.meatKGB_w.utilities.types.TypeErrInitAccount
import com.zaar.meatKGB_w.utilities.types.TypeKeyForStore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlin.coroutines.ContinuationInterceptor

class IdentificationVM(myContext: Context): BaseVM(myContext) {
    private var mldIsSavedRegData = MutableLiveData<Boolean>()
    fun ldIsSavedRegData(): LiveData<Boolean> = mldIsSavedRegData

    private var mldIsUpdatingData = MutableLiveData<Boolean>()
    fun ldIsUpdatingData(): LiveData<Boolean> = mldIsUpdatingData

    /**
     * initialization of the user account that has been saved in the local storage
     */
    fun initStoredAccount() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d("TAG", "MESSAGE: CurrentThread[${Thread.currentThread().name}]")
            val isExists = SharedPreferencesRepositoryImpl(myContext).containsPreferences(
                arrayOf(
                    TypeKeyForStore.KEY_USR_LOG.value,
                    TypeKeyForStore.KEY_USR_PASS.value,
                    TypeKeyForStore.KEY_ENTERPRISE_ID.value,
                    TypeKeyForStore.KEY_SHOP_ID.value,
                )
            )
            if (
                isExists.getBoolean(TypeKeyForStore.KEY_ENTERPRISE_ID.value)
                && isExists.getBoolean(TypeKeyForStore.KEY_USR_LOG.value)
                && isExists.getBoolean(TypeKeyForStore.KEY_USR_PASS.value)
            ) {
                mldStageDescriptionForAppEntry.postValue("idEnterprise is exists!")
                mldStageDescriptionForAppEntry.postValue("login and password is exists!")
                val bundleLogPass = SharedPreferencesRepositoryImpl(myContext).getPreferencesVal(
                    arrayOf(
                        TypeKeyForStore.KEY_USR_LOG.value,
                        TypeKeyForStore.KEY_USR_PASS.value,
                        TypeKeyForStore.KEY_ENTERPRISE_ID.value
                    )
                )
                val logPass = LogPass(
                    enterpriseId = bundleLogPass.getString(
                        TypeKeyForStore.KEY_ENTERPRISE_ID.value,
                        ""
                    ),
                    usrLogin = bundleLogPass.getString(TypeKeyForStore.KEY_USR_LOG.value, ""),
                    usrPass = bundleLogPass.getString(TypeKeyForStore.KEY_USR_PASS.value, "")
                )
                if (!logPass.isEmpty()) {
                    mldUserData.postValue(logPass)
                    Log.d(
                        "TAG",
                        "Dispatcher = ${coroutineContext[ContinuationInterceptor]},\n MESSAGE: CurrentThread[${Thread.currentThread().name}]"
                    )
                    obtainingValidSessionID(logPass, false)
                } else sendErrInitAccount(TypeErrInitAccount.REG_DATA_IS_EMPTY)
            } else sendErrInitAccount(TypeErrInitAccount.IS_NOT_EXISTS_STORE)
        }
    }

    fun initNewAccount(logPass: LogPass) {
        SharedPreferencesRepositoryImpl(myContext).deleteKeys(
            arrayOf(
                TypeKeyForStore.KEY_SESSION_ID.value,
                TypeKeyForStore.KEY_ENTERPRISE_ID.value,
                TypeKeyForStore.KEY_USR_LOG.value,
                TypeKeyForStore.KEY_USR_PASS.value
            )
        )
        obtainingValidSessionID(logPass, true)
    }

    /**
     * save in the sharedPreference
     */
    fun saveUserData(
        logPass: LogPass = LogPass(),
        sessionId: String = "",
    ) {
        val bundle = Bundle()
        if (!logPass.isEmpty()) {
            bundle.putString(TypeKeyForStore.KEY_USR_LOG.value, logPass.usrLogin)
            bundle.putString(TypeKeyForStore.KEY_USR_PASS.value, logPass.usrPass)
            bundle.putString(TypeKeyForStore.KEY_ENTERPRISE_ID.value, logPass.enterpriseId)
            mldStageDescriptionForAppEntry.value = "logPass is prepared for save"
        } else {
            mldStageDescriptionForAppEntry.value = "logPas is empty"
            mldIsSavedRegData.value = false
        }
        if (!sessionId.isNullOrEmpty()) {
            bundle.putString(TypeKeyForStore.KEY_SESSION_ID.value, sessionId)
            mldStageDescriptionForAppEntry.value = "sessionId is prepared for save"
        } else {
            mldStageDescriptionForAppEntry.value = "sessionId is null or empty"
            mldIsSavedRegData.value = false
        }
        mldIsSavedRegData.value = SharedPreferencesRepositoryImpl(myContext).setPreferences(bundle)
    }

    fun updatingData() {
        viewModelScope.launch(Dispatchers.IO) {
            Log.d(
                "TAG",
                "Dispatcher = ${coroutineContext[ContinuationInterceptor]},\n MESSAGE: CurrentThread[${Thread.currentThread().name}]"
            )
            val sessionId =
                SharedPreferencesRepositoryImpl(myContext)
                    .getPreferencesVal(arrayOf(TypeKeyForStore.KEY_SESSION_ID.value))
                    .getString(TypeKeyForStore.KEY_SESSION_ID.value, "") ?: ""
            val enterpriseId =
                SharedPreferencesRepositoryImpl(myContext)
                    .getPreferencesVal(arrayOf(TypeKeyForStore.KEY_ENTERPRISE_ID.value))
                    .getString(TypeKeyForStore.KEY_ENTERPRISE_ID.value, "") ?: ""
            if (enterpriseId.isNotEmpty() && sessionId.isNotEmpty()) {
                Log.d(
                    "TAG",
                    "Dispatcher = ${coroutineContext[ContinuationInterceptor]},\n MESSAGE: CurrentThread[${Thread.currentThread().name}]"
                )
                val result = updateDb(sessionId, enterpriseId)
                val value = result.await()
                mldIsUpdatingData.postValue(value)
            } else {
                mldStageDescriptionForAppEntry
                    .postValue("enterpriseId and sessionId not exists in store")
                mldIsUpdatingData.postValue(false)
            }
        }
    }

    private fun updateDb(
        sessionId: String,
        enterpriseId: String,
    ): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            if (isOnline()) {
                if (
                    UpdateWorkerUseCase(sessionId, enterpriseId, myContext)
                        .executeWithReplace()
                ) {
                    Log.d(
                        "TAG",
                        "Dispatcher = ${coroutineContext[ContinuationInterceptor]},\n MESSAGE: CurrentThread[${Thread.currentThread().name}]"
                    )
                    val idWorkshop = LocalDBRepositoryImpl(myContext).getIdWorkshop()
                    if (idWorkshop >= 0) {
                        mldStageDescriptionForAppEntry.postValue("updating user data - is ok")
                        val resUpdShop = UpdateShopUseCase(
                            sessionId = sessionId,
                            enterpriseId = enterpriseId,
                            idWorkshop = idWorkshop,
                            myContext = myContext
                        ).executeWithReplace()
                        val role = LocalDBRepositoryImpl(myContext).getIdRoleByShop()
                        val idOneMoreWorkshop =
                            if (role == 3L)
                                LocalDBRepositoryImpl(myContext).getIdMoreWorkshop()
                            else -1L
                        val resUpdProd =
                            UpdateProductUseCase(
                                sessionId = sessionId,
                                enterpriseId = enterpriseId,
                                idWorkshop = idWorkshop,
                                myContext = myContext
                            ).executeWithReplace()
                        if (idOneMoreWorkshop > 0) {
                            UpdateProductUseCase(
                                sessionId = sessionId,
                                enterpriseId = enterpriseId,
                                idWorkshop = idOneMoreWorkshop,
                                myContext = myContext
                            ).executeWithAppend()
                        }
                        if (resUpdShop)
                            mldStageDescriptionForAppEntry.postValue("updating shop data - is ok")
                        else mldStageDescriptionForAppEntry.postValue("updating shop data - is false")
                        if (resUpdProd)
                            mldStageDescriptionForAppEntry.postValue("updating products data - is ok")
                        else mldStageDescriptionForAppEntry.postValue("updating products data - is false")
                        resUpdProd && resUpdShop
                    } else {
                        mldStageDescriptionForAppEntry.postValue("updating data - is false")
                        false
                    }
                } else false
            } else false
        }
    }

    fun deleteUserData() {
        SharedPreferencesRepositoryImpl(myContext).clearStore()
        mldIsSavedRegData.value = false
    }

    private fun sendErrInitAccount(typeErr: TypeErrInitAccount) {
        when (typeErr) {
            TypeErrInitAccount.REG_DATA_IS_EMPTY -> {
                mldStageDescriptionForAppEntry.postValue("Fields of registration data is empty!")
            }

            TypeErrInitAccount.IS_NOT_EXISTS_STORE -> {
                mldStageDescriptionForAppEntry.postValue("idEnterprise or login or password is not contains in the store!")
            }
        }
        mldStageDescriptionForAppEntry.postValue("PLEASE INSERT REGISTRATION DATA!")
        mldIsInitExistsEnterprise.postValue(false)
    }
}