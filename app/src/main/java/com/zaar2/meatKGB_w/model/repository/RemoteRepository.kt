package com.zaar2.meatKGB_w.model.repository

import com.zaar2.meatKGB_w.data.LogPass
import com.zaar2.meatKGB_w.model.remote.entityRemote.ProductRemote
import com.zaar2.meatKGB_w.model.remote.entityRemote.RecordRemote
import com.zaar2.meatKGB_w.model.remote.entityRemote.ShopRemote
import com.zaar2.meatKGB_w.model.remote.entityRemote.UserRemote

interface RemoteRepository {
    suspend fun identificationUser(logPass: LogPass): String
    suspend fun verificationSessionId(sessionId: String): Boolean
    suspend fun getWorker(sessionId: String, enterpriseID: String): UserRemote?
    suspend fun getShop(sessionId: String, enterpriseID: String, idWorkshop: Long): ShopRemote?
    suspend fun getProducts(sessionId: String, enterpriseID: String, idWorkshop: Long): List<ProductRemote>?
    suspend fun postRecord(recordRemote: RecordRemote, sessionId: String): List<RecordRemote>?
    suspend fun deleteRecord(sessionId: String, idRecord: Long, idUser: Long, enterpriseId: String): List<RecordRemote>?
    suspend fun getRecords(sessionId: String, idUser: Long, idEnterprise: String): List<RecordRemote>?
}