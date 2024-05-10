package com.zaar.meatKGB_w.model.repository

import com.zaar.meatKGB_w.data.LogPass
import com.zaar.meatKGB_w.model.remote.api_retrofit.api.ApiInterface
import com.zaar.meatKGB_w.model.remote.api_retrofit.builder.ApiClient
import com.zaar.meatKGB_w.model.remote.api_retrofit.entityApi.ProductApi
import com.zaar.meatKGB_w.model.remote.api_retrofit.entityApi.RecordApi
import com.zaar.meatKGB_w.model.remote.api_retrofit.entityApi.ShopApi
import com.zaar.meatKGB_w.model.remote.api_retrofit.entityApi.UserApi

class RemoteRepositoryImpl: RemoteRepository {

    override suspend fun identificationUser(
        logPass: LogPass
    ): String =
        try {
            val response = ApiClient.getClient()?.create(ApiInterface::class.java)
                ?.identificationUser(logPass)
            if (response != null && response.isSuccessful) {
                response.body() ?: ""
            } else ""
        } catch (e: Exception) {
            ""
        }

    override suspend fun verificationSessionId(
        sessionId: String
    ): Boolean =
        try {
            val response = (ApiClient.getClient()?.create(ApiInterface::class.java)
                ?.identificationSession(sessionId))
            if (response != null && response.isSuccessful) {
                response.body().equals("1")
            } else false
        } catch (e: Exception) {
            false
        }

    override suspend fun getWorker(
        sessionId: String, enterpriseID: String
    ): UserApi? =
        try {
            val response = (ApiClient.getClient()?.create(ApiInterface::class.java))
                ?.getWorkerBySession(sessionId, enterpriseID)
            if (response != null) {
                if (response.isSuccessful) {
                    response.body()?.get(0)
                } else null
            } else null
        } catch (exception: Exception) {
            null
        }

    override suspend fun getShop(
        sessionId: String,
        enterpriseID: String,
        idWorkshop: Long
    ): ShopApi? =
        try {
            val response = (ApiClient.getClient()?.create(ApiInterface::class.java))
                ?.getShop(
                    sessionID = sessionId,
                    idWorkshop = idWorkshop,
                    enterpriseID = enterpriseID
                )
            if (response != null) {
                if (response.isSuccessful) {
                    response.body()?.get(0)
                } else null
            } else null
        } catch (e: Exception) {
            null
        }

    override suspend fun getProducts(
        sessionId: String,
        enterpriseID: String,
        idWorkshop: Long
    ): List<ProductApi>? =
        try {
            val response = (ApiClient.getClient()?.create(ApiInterface::class.java))
                ?.getProducts(sessionId, enterpriseID, idWorkshop)
            if (response != null) {
                if (response.isSuccessful) {
                    response.body()
                } else null
            } else null
        } catch (e: Exception) {
            null
        }

    override suspend fun postRecord(
        recordApi: RecordApi,
        sessionId: String,
    ): List<RecordApi>? =
        try {
            val response = (ApiClient.getClient()?.create(ApiInterface::class.java))
                ?.addRecord(sessionId, recordApi)
            response?.let {
                if (it.isSuccessful) {
                    response.body()
                } else null
            }
        } catch (e: Exception) {
            null
        }

    override suspend fun deleteRecord(
        sessionId: String, idRecord: Long, idUser: Long, enterpriseId: String
    ): List<RecordApi>? =
        try {
            val response = (ApiClient.getClient()?.create(ApiInterface::class.java))
                ?.deleteRecord(sessionId, idRecord, idUser, enterpriseId)
            response?.let {
                if (it.isSuccessful) {
                    response.body()
                } else null
            }
        } catch (e: Exception) {
            null
        }

    override suspend fun getRecords(
        sessionId: String,
        idUser: Long,
        idEnterprise: String,
    ): List<RecordApi>? =
        try {
            val response = (ApiClient.getClient()?.create(ApiInterface::class.java))
                ?.getRecords(sessionId, idUser, idEnterprise)
            response?.let {
                if (it.isSuccessful) {
                    response.body()
                } else null
            }
        } catch (e: Exception) {
            null
        }
}