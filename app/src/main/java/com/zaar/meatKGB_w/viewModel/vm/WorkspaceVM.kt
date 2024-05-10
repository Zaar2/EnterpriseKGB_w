package com.zaar.meatKGB_w.viewModel.vm

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.zaar.meatKGB_w.data.UserDescription
import com.zaar.meatKGB_w.model.entityUi.RecordUi
import com.zaar.meatKGB_w.model.mappers.apiToDb.RecordMapperApiToDb
import com.zaar.meatKGB_w.model.mappers.dbToUi.RecordMapperDbToUi
import com.zaar.meatKGB_w.model.mappers.uiToApi.RecordMapperUiToApi
import com.zaar.meatKGB_w.model.remote.api_retrofit.entityApi.RecordApi
import com.zaar.meatKGB_w.model.repository.LocalDBRepositoryImpl
import com.zaar.meatKGB_w.model.repository.RemoteRepositoryImpl
import com.zaar.meatKGB_w.model.repository.SharedPreferencesRepositoryImpl
import com.zaar.meatKGB_w.utilities.types.TypeKeyForStore
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class WorkspaceVM(myContext: Context): BaseVM(myContext) {

    private val mldUserDescription = MutableLiveData<UserDescription>()
    fun ldUserDescription(): LiveData<UserDescription> = mldUserDescription

    private val mldProductsList = MutableLiveData<List<String>>()
    fun ldProductsList(): LiveData<List<String>> = mldProductsList

    private val mldMeProduct = MutableLiveData<String>()
    fun ldMeProduct(): LiveData<String> = mldMeProduct

    private val mldIsPostRecordRemote = MutableLiveData<Boolean>()
    fun ldIsPostRecordRemote(): LiveData<Boolean> = mldIsPostRecordRemote

    private val mldRecords = MutableLiveData<List<RecordUi>?>()
    fun ldRecords(): LiveData<List<RecordUi>?> = mldRecords

    private val mldShopId = MutableLiveData<Long>()
    fun ldShopId(): LiveData<Long> = mldShopId

    private val mldAccuracy = MutableLiveData<Int>()
    fun ldAccuracy(): LiveData<Int> = mldAccuracy

    //region INITIALIZATION FUNCTIONS
    fun initDescriptionUser() {
        viewModelScope.launch {
            mldUserDescription.value = async(Dispatchers.IO) {
                LocalDBRepositoryImpl(myContext).getUserDescription()
            }.await()
        }
    }

    fun initItemsSpinnerProduct() {
        viewModelScope.launch {
            mldProductsList.value = async(Dispatchers.IO) {
                LocalDBRepositoryImpl(myContext).getProduct()
            }.await()
        }
    }

    fun getProductMe(nameProduct: String) {
        viewModelScope.launch {
            mldMeProduct.value = async(Dispatchers.IO) {
                LocalDBRepositoryImpl(myContext).getMeByProduct(nameProduct)
            }.await()
        }
    }
    //endregion

    //    region
    fun addRecord(recordUi: RecordUi) {
        viewModelScope.launch {
            var sessionId = ""
            val bundle = SharedPreferencesRepositoryImpl(myContext).getPreferencesVal(
                arrayOf(TypeKeyForStore.KEY_SESSION_ID.value)
            )
            if (!bundle.isEmpty) sessionId =
                bundle.getString(TypeKeyForStore.KEY_SESSION_ID.value, "")
            if (sessionId.isNotEmpty()) {
                if (isOnline()) {
                    val recordApi = RecordMapperUiToApi(myContext).executeSuspend(recordUi)
                    val recordsApi = if (recordApi.isReady()) {
                        RemoteRepositoryImpl().postRecord(
                            recordApi,
                            sessionId
                        )
                    } else return@launch
                    if (!recordsApi.isNullOrEmpty()) {
                        val recordsDb = recordsApi.let { RecordMapperApiToDb().execute(it) }
                        val arrIds =
                            recordsDb.let { LocalDBRepositoryImpl(myContext).setRecord(it) }
                        if (arrIds != null && arrIds.isNotEmpty()) {
                            val recordsUi = RecordMapperDbToUi(myContext).executeSuspend(recordsDb)
                            mldRecords.value = recordsUi
                        }
                    }
                }
            }
        }
    }

    fun delete(idRecord: Long) {
        viewModelScope.launch {
            var sessionId = ""
            var loginUser = ""
            var idEnterprise = ""
            val recordsApi: List<RecordApi>?
            val bundle = SharedPreferencesRepositoryImpl(myContext).getPreferencesVal(
                arrayOf(
                    TypeKeyForStore.KEY_SESSION_ID.value,
                    TypeKeyForStore.KEY_USR_LOG.value,
                    TypeKeyForStore.KEY_ENTERPRISE_ID.value,
                )
            )
            if (!bundle.isEmpty) {
                sessionId =
                    bundle.getString(TypeKeyForStore.KEY_SESSION_ID.value, "")
                loginUser =
                    bundle.getString(TypeKeyForStore.KEY_USR_LOG.value, "")
                idEnterprise =
                    bundle.getString(TypeKeyForStore.KEY_ENTERPRISE_ID.value, "")
            }
            if (sessionId.isNotEmpty() && loginUser.isNotEmpty() && idEnterprise.isNotEmpty()) {
                if (isOnline()) {
                    val idUser = LocalDBRepositoryImpl(myContext).getUserIdByLogin(loginUser)
                    recordsApi = RemoteRepositoryImpl().deleteRecord(
                        sessionId,
                        idRecord,
                        idUser,
                        idEnterprise
                    )
                } else return@launch
            } else return@launch
            if (!recordsApi.isNullOrEmpty()) {
                val recordsDb = recordsApi.let { RecordMapperApiToDb().execute(it) }
                val arrIds = recordsDb.let { LocalDBRepositoryImpl(myContext).setRecord(it) }
                if (arrIds != null && arrIds.isNotEmpty()) {
                    val recordsUi = RecordMapperDbToUi(myContext).executeSuspend(recordsDb)
                    mldRecords.value = recordsUi
                }
            }
        }
    }

    fun update() {
        viewModelScope.launch {
            var sessionId = ""
            var loginUser = ""
            var idEnterprise = ""
            val recordsApi: List<RecordApi>?
            val bundle = SharedPreferencesRepositoryImpl(myContext).getPreferencesVal(
                arrayOf(
                    TypeKeyForStore.KEY_SESSION_ID.value,
                    TypeKeyForStore.KEY_USR_LOG.value,
                    TypeKeyForStore.KEY_ENTERPRISE_ID.value,
                )
            )
            if (!bundle.isEmpty) {
                sessionId =
                    bundle.getString(TypeKeyForStore.KEY_SESSION_ID.value, "")
                loginUser =
                    bundle.getString(TypeKeyForStore.KEY_USR_LOG.value, "")
                idEnterprise =
                    bundle.getString(TypeKeyForStore.KEY_ENTERPRISE_ID.value, "")
            }
            val idUser = LocalDBRepositoryImpl(myContext).getUserIdByLogin(loginUser)
            if (sessionId.isNotEmpty() && loginUser.isNotEmpty() && idEnterprise.isNotEmpty()) {
                if (isOnline()) {
                    recordsApi =
                        RemoteRepositoryImpl().getRecords(sessionId, idUser, idEnterprise)
                } else return@launch
            } else return@launch
            if (!recordsApi.isNullOrEmpty()) {
                val recordsDb = recordsApi.let { RecordMapperApiToDb().execute(it) }
                val arrIds = recordsDb.let { LocalDBRepositoryImpl(myContext).setRecord(it) }
                if (arrIds != null && arrIds.isNotEmpty()) {
                    val recordsUi = RecordMapperDbToUi(myContext).executeSuspend(recordsDb)
                    mldRecords.value = recordsUi
                }
            }
        }
    }

    fun getShopId(productName: String) {
        viewModelScope.launch {
            mldShopId.value = LocalDBRepositoryImpl(myContext).getShopIdByProductName(productName)
        }
    }

    fun getAccuracy(productName: String) {
        viewModelScope.launch {
            mldAccuracy.value =
                LocalDBRepositoryImpl(myContext).getAccuracyByProductName(productName)
        }
    }
//    endregion
}