package com.zaar2.meatKGB_w.model.useCase

import android.content.Context
import com.zaar2.meatKGB_w.model.local.api_room.entityDb.ProductDb
import com.zaar2.meatKGB_w.model.mappers.remoteToDb.ProductMapperRemoteToDb
import com.zaar2.meatKGB_w.model.repository.LocalDBRepositoryImpl
import com.zaar2.meatKGB_w.model.repository.RemoteRepositoryImpl

class UpdateProductUseCase(
    private val sessionId: String,
    private val enterpriseId: String,
    private val idWorkshop: Long = -1,
    private val myContext: Context
) {
    suspend fun executeWithReplace(): Boolean =
        getsAndProcessingRemoteProducts()?.let {
            LocalDBRepositoryImpl(myContext).productsInsertWithReplace(it)
                .size == it.size
        } ?: false

    suspend fun executeWithAppend(): Boolean =
        getsAndProcessingRemoteProducts()?.let {
            LocalDBRepositoryImpl(myContext).productsInsert(it)
                .size == it.size
            } ?: false

    private suspend fun getsAndProcessingRemoteProducts(): MutableList<ProductDb>? {
        val productsRemote = RemoteRepositoryImpl().getProducts(
            sessionId = sessionId,
            idWorkshop = idWorkshop,
            enterpriseID = enterpriseId
        )
        return if (!productsRemote.isNullOrEmpty()) {
            productsRemote.run {
                val productsOutput = mutableListOf<ProductDb>()
                forEach {
                    productsOutput.add(ProductMapperRemoteToDb(it).execute())
                }
                productsOutput
            }
        } else null
    }
}