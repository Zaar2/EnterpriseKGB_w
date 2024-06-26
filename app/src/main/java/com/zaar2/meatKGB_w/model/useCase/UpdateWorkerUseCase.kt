package com.zaar2.meatKGB_w.model.useCase

import android.content.Context
import android.util.Log
import com.zaar2.meatKGB_w.model.mappers.remoteToDb.UserMapperRemoteToDb
import com.zaar2.meatKGB_w.model.repository.LocalDBRepositoryImpl
import com.zaar2.meatKGB_w.model.repository.RemoteRepositoryImpl
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.coroutineContext

class UpdateWorkerUseCase(
    private val sessionId: String,
    private val enterpriseId: String,
    private val myContext: Context
) {
    suspend fun executeWithReplace(): Boolean {
        Log.d("TAG", "Dispatcher = ${coroutineContext[ContinuationInterceptor]},\n MESSAGE: CurrentThread[${Thread.currentThread().name}]")
        val userApi = RemoteRepositoryImpl().getWorker(
            sessionId = sessionId,
            enterpriseID = enterpriseId
        )
        return if (userApi != null) {
            myContext.let {
                LocalDBRepositoryImpl(it)
                    .userInsertWithReplace(
                        UserMapperRemoteToDb(userApi).execute()
                    )
            } >= 0
        } else false
    }
}