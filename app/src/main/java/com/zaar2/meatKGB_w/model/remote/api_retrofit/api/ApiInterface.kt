package com.zaar2.meatKGB_w.model.remote.api_retrofit.api


import com.zaar2.meatKGB_w.data.LogPass
import com.zaar2.meatKGB_w.model.local.api_room.entityDb.EnterpriseDb
import com.zaar2.meatKGB_w.model.remote.entityRemote.ProductRemote
import com.zaar2.meatKGB_w.model.remote.entityRemote.RecordRemote
import com.zaar2.meatKGB_w.model.remote.entityRemote.ShopRemote
import com.zaar2.meatKGB_w.model.remote.entityRemote.UserRemote
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiInterface {
    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("record/{sessionID}")
    suspend fun addRecord(
        @Path(value="sessionID") sessionId: String,
        @Body recordRemote: RecordRemote,
    ): Response<List<RecordRemote>>

    @GET("record/{sessionID}")
    suspend fun getRecords(
        @Path(value = "sessionID") sessionID: String,
        @Query(value = "idWorker") idUser: Long,
        @Query(value = "enterpriseId") idEnterprise: String,
    ): Response<List<RecordRemote>>

    @DELETE("record/{sessionID}")
    suspend fun deleteRecord(
        @Path(value="sessionID") sessionId: String,
        @Query(value = "id") idRecord: Long,
        @Query(value = "idUser") idUser: Long,
        @Query(value = "enterpriseId") enterpriseId: String,
    ): Response<List<RecordRemote>>

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("identification")
    suspend fun identificationUser(
        @Body logPass: LogPass
    ): Response<String>

    @GET("session/{sessionID}")
    suspend fun identificationSession(
        @Path(value = "sessionID") sessionID: String
    ): Response<String>

    @GET("shop/{id_workshop}/{sessionID}")
    suspend fun getShop(
        @Path(value = "sessionID") sessionID: String,
        @Path(value = "id_workshop") idWorkshop: Long,
        @Query("enterpriseId") enterpriseID: String,
    ): Response<List<ShopRemote>>

    @GET("worker/{sessionID}")
    suspend fun getWorkerBySession(
        @Path(value = "sessionID") sessionID: String,
        @Query("enterpriseId") enterpriseID: String
    ): Response<List<UserRemote>>

    @GET("enterprise/{cryptoID}/{sessionID}")
    suspend fun getEnterprise(
        @Path(value = "sessionID") sessionID: String,
        @Path(value = "cryptoID") enterpriseID: String
    ): Response<EnterpriseDb>

    @GET("product/{sessionID}")
    suspend fun getProducts(
        @Path(value = "sessionID") sessionID: String,
        @Query("enterpriseId") enterpriseID: String,
        @Query("id_workshop") idWorkshop: Long
    ): Response<List<ProductRemote>>

    @GET("reportShops/{sessionID}")
    suspend fun getRecordReport(
        @Path(value = "sessionID") sessionID: String,
//        @Query("type") type: String?,
        @Query("idWorker") idWorker: Long,
        @Query("enterpriseId") enterpriseId: String
    ): Response<List<RecordRemote>>
}