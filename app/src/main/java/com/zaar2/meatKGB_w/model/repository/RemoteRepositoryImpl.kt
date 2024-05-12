package com.zaar2.meatKGB_w.model.repository

import com.zaar2.meatKGB_w.data.LogPass
import com.zaar2.meatKGB_w.model.remote.api_retrofit.api.ApiInterface
import com.zaar2.meatKGB_w.model.remote.api_retrofit.builder.ApiClient
import com.zaar2.meatKGB_w.model.remote.entityRemote.ProductRemote
import com.zaar2.meatKGB_w.model.remote.entityRemote.RecordRemote
import com.zaar2.meatKGB_w.model.remote.entityRemote.ShopRemote
import com.zaar2.meatKGB_w.model.remote.entityRemote.UserRemote

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
    ): UserRemote? =
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
    ): ShopRemote? =
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
    ): List<ProductRemote>? =
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
        recordRemote: RecordRemote,
        sessionId: String,
    ): List<RecordRemote>? =
        try {
            val response = (ApiClient.getClient()?.create(ApiInterface::class.java))
                ?.addRecord(sessionId, recordRemote)
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
    ): List<RecordRemote>? =
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
    ): List<RecordRemote>? =
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