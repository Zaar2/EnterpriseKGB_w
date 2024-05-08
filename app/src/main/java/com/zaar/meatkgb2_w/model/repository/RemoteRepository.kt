package com.zaar.meatkgb2_w.model.repository

import com.zaar.meatkgb2_w.data.LogPass
import com.zaar.meatkgb2_w.data.entity.Enterprise
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.RecordDb
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.ProductApi
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.RecordApi
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.ShopApi
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.UserApi
import retrofit2.http.Path
import retrofit2.http.Query

interface RemoteRepository {
    suspend fun identificationUser(logPass: LogPass): String
    suspend fun verificationSessionId(sessionId: String): Boolean
    suspend fun getWorker(sessionId: String, enterpriseID: String): UserApi?
    suspend fun getShop(sessionId: String, enterpriseID: String, idWorkshop: Long): ShopApi?
    suspend fun getProducts(sessionId: String, enterpriseID: String, idWorkshop: Long): List<ProductApi>?
    suspend fun postRecord(recordApi: RecordApi, sessionId: String): List<RecordApi>?
    suspend fun deleteRecord(sessionId: String, idRecord: Long, idUser: Long, enterpriseId: String): List<RecordApi>?
    suspend fun getRecords(sessionId: String, idUser: Long, idEnterprise: String): List<RecordApi>?
}