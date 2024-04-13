package com.zaar.meatkgb2_w.viewModel.vm

import android.content.Context
import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zaar.meatkgb2_w.data.LogPass
import com.zaar.meatkgb2_w.model.repository.LocalDBRepositoryImpl
import com.zaar.meatkgb2_w.model.repository.SharedPreferencesRepositoryImpl
import com.zaar.meatkgb2_w.model.useCase.UpdateProductUseCase
import com.zaar.meatkgb2_w.model.useCase.UpdateShopUseCase
import com.zaar.meatkgb2_w.model.useCase.UpdateWorkerUseCase
import com.zaar.meatkgb2_w.utilities.types.TypeErrInitAccount
import com.zaar.meatkgb2_w.utilities.types.TypeKeyForStore
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class IdentificationVM(myContext: Context): BaseVM(myContext) {
    private var mldIsSavedRegData = MutableLiveData<Boolean>()
    fun ldIsSavedRegData(): LiveData<Boolean> = mldIsSavedRegData

    private var mldIsUpdatingData = MutableLiveData<Boolean>()
    fun ldIsUpdatingData(): LiveData<Boolean> = mldIsUpdatingData

    fun initStoredAccount() {
        viewModelScope.launch {
            val isExists = SharedPreferencesRepositoryImpl(myContext).containsPreferences(
                arrayOf(
                    TypeKeyForStore.KEY_USR_LOG.value,
                    TypeKeyForStore.KEY_USR_PASS.value,
                    TypeKeyForStore.KEY_ENTERPRISE_ID.value
                )
            )
            if (
                isExists.getBoolean(TypeKeyForStore.KEY_ENTERPRISE_ID.value)
                && isExists.getBoolean(TypeKeyForStore.KEY_USR_LOG.value)
                && isExists.getBoolean(TypeKeyForStore.KEY_USR_PASS.value)
            ) {
                mldStageDescriptionForAppEntry.value = "idEnterprise is exists!"
                mldStageDescriptionForAppEntry.value = "login and password is exists!"
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
                    mldUserData.value = logPass
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
        viewModelScope.launch {
            val sessionId =
                SharedPreferencesRepositoryImpl(myContext)
                    .getPreferencesVal(arrayOf(TypeKeyForStore.KEY_SESSION_ID.value))
                    .getString(TypeKeyForStore.KEY_SESSION_ID.value, "") ?: ""
            val enterpriseId =
                SharedPreferencesRepositoryImpl(myContext)
                    .getPreferencesVal(arrayOf(TypeKeyForStore.KEY_ENTERPRISE_ID.value))
                    .getString(TypeKeyForStore.KEY_ENTERPRISE_ID.value, "") ?: ""
            if (enterpriseId.isNotEmpty() && sessionId.isNotEmpty()) {
                val result = updateDb(sessionId, enterpriseId)
                mldIsUpdatingData.value = result.await()
            } else {
                mldStageDescriptionForAppEntry.value="enterpriseId and sessionId not exists in store"
                mldIsUpdatingData.value = false
            }
        }
    }

    private fun updateDb(
        sessionId: String,
        enterpriseId: String,
    ): Deferred<Boolean> {
        return viewModelScope.async(Dispatchers.IO) {
            if (
                async { UpdateWorkerUseCase(sessionId, enterpriseId, myContext).executeWithReplace() }.await()
            ) {
                val idWorkshop =
                    async { LocalDBRepositoryImpl(myContext).getIdWorkshop() }.await()
                if (idWorkshop >= 0) {
                    val resUpdShop = async {
                        UpdateShopUseCase(
                            sessionId = sessionId,
                            enterpriseId = enterpriseId,
                            idWorkshop = idWorkshop,
                            myContext = myContext
                        ).executeWithReplace()
                    }.await()
                    val idOneMoreWorkshop =
                        async {
                            val role = LocalDBRepositoryImpl(myContext).getIdRoleByShop()
                            if (role == 3L)
                                LocalDBRepositoryImpl(myContext).getIdMoreWorkshop()
                            else -1L
                        }.await()
                    val resUpdProd = async {
                        UpdateProductUseCase(
                            sessionId = sessionId,
                            enterpriseId = enterpriseId,
                            idWorkshop = idWorkshop,
                            myContext = myContext
                        ).executeWithReplace()
                    }.await()
                    if (idOneMoreWorkshop > 0) {
                        launch {
                            UpdateProductUseCase(
                                sessionId = sessionId,
                                enterpriseId = enterpriseId,
                                idWorkshop = idOneMoreWorkshop,
                                myContext = myContext
                            ).executeWithAppend()
                        }.join()
                    }
                    resUpdProd && resUpdShop
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
                mldStageDescriptionForAppEntry.value =
                    "Fields of registration data is empty!"
            }

            TypeErrInitAccount.IS_NOT_EXISTS_STORE -> {
                mldStageDescriptionForAppEntry.value =
                    "idEnterprise or login or password is not contains in the store!"
            }
        }
        mldStageDescriptionForAppEntry.value = "PLEASE INSERT REGISTRATION DATA!"
        mldIsInitExistsEnterprise.value = false
    }
}