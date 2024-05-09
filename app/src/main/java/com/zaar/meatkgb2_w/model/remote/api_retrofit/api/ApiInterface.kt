package com.zaar.meatkgb2_w.model.remote.api_retrofit.api


import com.zaar.meatkgb2_w.data.LogPass
import com.zaar.meatkgb2_w.model.local.api_room.entityDb.EnterpriseDb
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.ProductApi
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.RecordApi
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.ShopApi
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.UserApi
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
        @Body recordApi: RecordApi,
    ): Response<List<RecordApi>>

    @GET("record/{sessionID}")
    suspend fun getRecords(
        @Path(value = "sessionID") sessionID: String,
        @Query(value = "idWorker") idUser: Long,
        @Query(value = "enterpriseId") idEnterprise: String,
    ): Response<List<RecordApi>>

    @DELETE("record/{sessionID}")
    suspend fun deleteRecord(
        @Path(value="sessionID") sessionId: String,
        @Query(value = "id") idRecord: Long,
        @Query(value = "idUser") idUser: Long,
        @Query(value = "enterpriseId") enterpriseId: String,
    ): Response<List<RecordApi>>

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
    ): Response<List<ShopApi>>

    @GET("worker/{sessionID}")
    suspend fun getWorkerBySession(
        @Path(value = "sessionID") sessionID: String,
        @Query("enterpriseId") enterpriseID: String
    ): Response<List<UserApi>>

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
    ): Response<List<ProductApi>>

    @GET("reportShops/{sessionID}")
    suspend fun getRecordReport(
        @Path(value = "sessionID") sessionID: String,
//        @Query("type") type: String?,
        @Query("idWorker") idWorker: Long,
        @Query("enterpriseId") enterpriseId: String
    ): Response<List<RecordApi>>
}