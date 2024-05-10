package com.zaar.meatKGB_w.model.useCase

import android.content.Context
import android.util.Log
import com.zaar.meatKGB_w.model.mappers.apiToDb.ShopMapperApiToDb
import com.zaar.meatKGB_w.model.repository.LocalDBRepositoryImpl
import com.zaar.meatKGB_w.model.repository.RemoteRepositoryImpl
import kotlin.coroutines.ContinuationInterceptor
import kotlin.coroutines.coroutineContext

class UpdateShopUseCase(
    private val sessionId: String,
    private val enterpriseId: String,
    private val idWorkshop: Long = -1,
    private val myContext: Context
) {
    suspend fun executeWithReplace(): Boolean {
        Log.d("TAG", "Dispatcher = ${coroutineContext[ContinuationInterceptor]},\n MESSAGE: CurrentThread[${Thread.currentThread().name}]")
        val shopApi = RemoteRepositoryImpl().getShop(
            sessionId = sessionId,
            idWorkshop = idWorkshop,
            enterpriseID = enterpriseId
        )
        return if (shopApi != null) {
            myContext.let {
                LocalDBRepositoryImpl(it)
                    .shopInsertWithReplace(ShopMapperApiToDb(shopApi).execute())
            } >= 0
        } else false
    }
}