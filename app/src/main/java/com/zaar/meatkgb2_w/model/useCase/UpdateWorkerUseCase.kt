package com.zaar.meatkgb2_w.model.useCase

import android.content.Context
import com.zaar.meatkgb2_w.model.mappers.apiToDb.UserMapperApiToDb
import com.zaar.meatkgb2_w.model.repository.LocalDBRepositoryImpl
import com.zaar.meatkgb2_w.model.repository.RemoteRepositoryImpl

class UpdateWorkerUseCase(
    private val sessionId: String,
    private val enterpriseId: String,
    private val myContext: Context
) {
    suspend fun executeWithReplace(): Boolean {
        val userApi = RemoteRepositoryImpl().getWorker(
            sessionId = sessionId,
            enterpriseID = enterpriseId
        )
        return if (userApi != null) {
            myContext.let {
                LocalDBRepositoryImpl(it)
                    .userInsertWithReplace(
                        UserMapperApiToDb(userApi).execute()
                    )
            } >= 0
        } else false
    }
}