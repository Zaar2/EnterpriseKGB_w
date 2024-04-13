package com.zaar.meatkgb2_w.model.useCase

import android.content.Context
import com.zaar.meatkgb2_w.model.mappers.apiToDb.ShopMapperApiToDb
import com.zaar.meatkgb2_w.model.repository.LocalDBRepositoryImpl
import com.zaar.meatkgb2_w.model.repository.RemoteRepositoryImpl

class UpdateShopUseCase(
    private val sessionId: String,
    private val enterpriseId: String,
    private val idWorkshop: Long = -1,
    private val myContext: Context
) {
    suspend fun executeWithReplace(): Boolean {
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