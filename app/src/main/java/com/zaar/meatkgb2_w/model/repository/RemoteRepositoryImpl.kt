package com.zaar.meatkgb2_w.model.repository

import com.zaar.meatkgb2_w.data.LogPass
import com.zaar.meatkgb2_w.model.remote.api_retrofit.api.ApiInterface
import com.zaar.meatkgb2_w.model.remote.api_retrofit.builder.ApiClient
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.ProductApi
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.ShopApi
import com.zaar.meatkgb2_w.model.remote.api_retrofit.entityApi.UserApi

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
        }
        catch (exception: Exception) {
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
}